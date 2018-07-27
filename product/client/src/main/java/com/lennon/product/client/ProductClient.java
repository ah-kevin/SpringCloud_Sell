package com.lennon.product.client;

import com.lennon.product.common.DecreaseStockInput;
import com.lennon.product.common.ProductInfoOutput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "product")
public interface ProductClient {

    @PostMapping("/product/listForOrder")
     List<ProductInfoOutput> listForOrder(List<String> productIdList);

    @PostMapping("/product/decreaseStock")
     void decreaseStock(@RequestBody List<DecreaseStockInput> decreaseStockInputList);

}
