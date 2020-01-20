package com.example.ecom.controller;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="merchantPrice",url = "http://localhost:8089")
public interface MerchantCall {
//    @GetMapping("/view/id[]")
//
}
