package com.lennon.product.service;

import com.lennon.product.ProductApplicationTests;
import com.lennon.product.dataobject.ProductInfo;
import com.lennon.product.service.impl.ProductServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@Component
public class ProductServiceTest  extends ProductApplicationTests {
    @Autowired
    private ProductServiceImpl productService;
    @Test
    public void findOne() {
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> productInfoList = productService.findUpAll();
        Assert.assertTrue(productInfoList.size()>0);
    }
    @Test
    public void findList() {
        List<ProductInfo> productInfoList = productService.findList(Arrays.asList("157875196366160022","157875227953464068"));
        Assert.assertTrue(productInfoList.size()>0);
    }

    @Test
    public void findAll() {
    }

    @Test
    public void save() {
    }

}