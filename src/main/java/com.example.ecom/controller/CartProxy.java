package com.example.ecom.controller;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="cart",url = "http://172.16.20.172:8085")
public interface CartProxy {
//    @GetMapping("/view/id[]")
    @GetMapping("/order/productCount/{productId}")
    long viewProductCount(@PathVariable("productId") String productId);
//
}
