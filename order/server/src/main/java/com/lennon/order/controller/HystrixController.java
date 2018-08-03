package com.lennon.order.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
@DefaultProperties(defaultFallback = "defaultFallback")
public class HystrixController {
//    @HystrixCommand(fallbackMethod = "fallback")
    @GetMapping("/getProductInfoList")
    public String getProductInfoList(){
        RestTemplate restTemplate =  new RestTemplate();
        return restTemplate.postForObject("http://localhost:8081/product/listForOrder", Arrays.asList("164103465734242707")
        ,String.class);
    }

    public String fallback(){
        return "太拥挤,请稍后再试~";
    }
    public String defaultFallback(){
        return "默认提示太拥挤,请稍后再试~";
    }
}
