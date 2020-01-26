package com.example.ecom.controller;

import com.example.ecom.dto.*;
import com.example.ecom.services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "merchant", url = "http://172.16.20.113:8081")
public interface ProductProxy {

    @GetMapping("/merchant/merchants/{productId}")
    List<MerchantProductDTO> viewMerchantByProductId(@PathVariable("productId") String productId);

    @GetMapping("/merchant/viewProductIdByMerchantId/{merchantId}")
    List<String> viewProduct(@PathVariable("merchantId") String merchantId);

//    @PostMapping("/productMerchant")
//    Boolean addMerchantProduct(@RequestBody MerchantDTO merchantDTO);

    @PostMapping("/merchant/editInventory/")
    void editInventory(@RequestBody MerchantDTO merchantDTO);

//    @GetMapping("viewProductsByMerchantId/{merchantId}")
//    List<ProductsDTO> viewProductsByMerchantId(@PathVariable("merchantId") String merchantId);

    @GetMapping("/merchant/getRatingByMerchantId/{merchantId}")
    double getRatingByMerchantId(@PathVariable("merchantId") String merchantId);

    @GetMapping("merchant/getStockByPidMid/{merchantId}/{productId}")
    int getStockByPidMid(@PathVariable("merchantId") String merchantId,@PathVariable("productId") String productId);
}
