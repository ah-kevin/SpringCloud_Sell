package com.lennon.order.service.impl;

import com.lennon.order.dataobject.OrderDetail;
import com.lennon.order.dataobject.OrderMaster;
import com.lennon.order.dto.OrderDTO;
import com.lennon.order.enums.OrderStatusEnum;
import com.lennon.order.enums.PayStatusEnum;
import com.lennon.order.enums.ResultEnum;
import com.lennon.order.exception.OrderException;
import com.lennon.order.repository.OrderDetailRepository;
import com.lennon.order.repository.OrderMasterRepository;
import com.lennon.order.service.OrderService;
import com.lennon.order.utils.KeyUtil;
import com.lennon.product.client.ProductClient;
import com.lennon.product.common.DecreaseStockInput;
import com.lennon.product.common.ProductInfoOutput;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import sun.text.CollatorUtilities;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private ProductClient productClient;
     
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.getUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        //1. 查询商品(数量,价格,调用商品服务)
        List<String> productIdList = orderDTO.getOrderDetailList().stream()
                .map(OrderDetail::getProductId)
                .collect(Collectors.toList());
        List<ProductInfoOutput> productInfoList  = productClient.listForOrder(productIdList);
        //2. 计算订单总价
        for(OrderDetail orderDetail:orderDTO.getOrderDetailList()){
            for(ProductInfoOutput productInfo:productInfoList){
                if(productInfo.getProductId().equals(orderDetail.getProductId())){
                    // 单价*数量
                    orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(orderAmount);
                    // 订单详情入库
                    orderDetail.setDetailId(KeyUtil.getUniqueKey());
                    orderDetail.setOrderId(orderId);
                    BeanUtils.copyProperties(productInfo,orderDetail);
                    orderDetailRepository.save(orderDetail);
                }
            }

        }
        //3. 扣库存
        List<DecreaseStockInput> decreaseStockInputList = orderDTO.getOrderDetailList()
                .stream().map(e->new DecreaseStockInput(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productClient.decreaseStock(decreaseStockInputList);

        //4. 写入订单数据库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus((PayStatusEnum.WAIT.getCode()));
        orderMasterRepository.save(orderMaster);


        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(String orderId) {
        // 1. 先查询订单
        Optional<OrderMaster> orderMasterOption = orderMasterRepository.findById(orderId);
        if(!orderMasterOption.isPresent()){
            throw  new OrderException(ResultEnum.ORDER_NOT_EXIST);
        }
        // 2. 判断订单状态
        OrderMaster orderMaster = orderMasterOption.get();
        if(OrderStatusEnum.NEW.getCode()!=orderMaster.getOrderStatus()){
            throw  new OrderException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 3. 修改订单状态为完结
        orderMaster.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderMasterRepository.save(orderMaster);
        //4 .查询订单详情
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw  new OrderException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
