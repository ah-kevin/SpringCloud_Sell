package com.lennon.product.service;

import com.lennon.product.common.DecreaseStockInput;
import com.lennon.product.dataobject.ProductInfo;
import com.lennon.product.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductInfo findOne(String productId);

    /**
     * 查询所在架商品列表
     */
    List<ProductInfo> findUpAll();

    /**
     * 查询商品列表
     */
    List<ProductInfo> findList(List<String> productIdList);

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);
    // 加库存
    void increaseStock(List<CartDTO>cartDTOList);
//    // 减库存
    void decreaseStock(List<DecreaseStockInput>decreaseStockInputList);

}
