package com.lennon.product.service.impl;

import com.lennon.product.ProductApplicationTests;
import com.lennon.product.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
@Component
public class CategoryServiceImplTest extends ProductApplicationTests {
    @Autowired
    private CategoryServiceImpl categoryService;
    @Test
    public void findOne() {
    }

    @Test
    public void findAll() {
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(Arrays.asList(11,22));
        Assert.assertTrue(productCategoryList.size()>0);
    }

    @Test
    public void save() {
    }
}