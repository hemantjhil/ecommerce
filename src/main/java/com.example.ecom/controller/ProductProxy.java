package com.example.ecom.controller;

import com.example.ecom.dto.MerchantListingDTO;
import com.example.ecom.dto.MerchantProductDTO;
import com.example.ecom.services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "merchant", url = "http://172.16.20.113:8081")
public interface ProductProxy {

    @GetMapping("/merchant/{productId}")
    List<MerchantProductDTO> viewMerchantByProductId(@PathVariable("productId") String productId);

    @GetMapping("/viewProductIdByMerchantId/{merchantId}")
    List<String> viewProduct(@PathVariable("merchantId") String merchantId);

}
