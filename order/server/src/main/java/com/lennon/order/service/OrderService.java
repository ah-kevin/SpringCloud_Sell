package com.lennon.order.service;

import com.lennon.order.dto.OrderDTO;

public interface OrderService {
    /*创建订单*/
    OrderDTO create(OrderDTO orderDTO);
//    /*查询单个订单*/
//    OrderDTO findOne(String orderId);
//    /*查询订单列表*/
//    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);
//    /*取消订单*/
//    OrderDTO cancel(OrderDTO orderDTO);
    /*完结订单(只能卖家操作)*/
    OrderDTO finish(String orderId);
//    /*支付订单*/
//    OrderDTO paid(OrderDTO orderDTO);
}
