package com.lennon.product.repository;

import com.lennon.product.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {
    @Autowired
    private ProductInfoRepository repository;
    @Test
    public void findByProductStatus() {
        List<ProductInfo> productInfoList=repository.findByProductStatus(0);
        Assert.assertNotEquals(0,productInfoList.size());
    }
    @Test
    public void findByProductIdIn() {
        List<ProductInfo> list =repository.findByProductIdIn(Arrays.asList("157875196366160022","157875227953464068"));
        Assert.assertTrue(list.size()>0);
    }

}
