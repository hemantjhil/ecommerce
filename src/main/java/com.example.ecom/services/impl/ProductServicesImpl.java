package com.example.ecom.services.impl;

import com.example.ecom.controller.ProductProxy;
import com.example.ecom.dto.MerchantProductDTO;
import com.example.ecom.entity.Product;
import com.example.ecom.repository.ProductRepository;
import com.example.ecom.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServicesImpl implements ProductServices {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductProxy productProxy;
    @Override
    public Product save(Product product){
        return productRepository.insert(product);
    }


    @Override
    public List<Product> viewProductsByCategoryId(String categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        if(null!=products) return products;
        return new ArrayList<>();
    }

    @Override
    public Iterable<Product> findAllById(Iterable<String> iterable) {
        return null;
    }

    @Override
    public Product viewProducById(String id){
        Product product=productRepository.findByProductId(id);
        if(null!=product) return product;
        return new Product();
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if(null!=products) return products;
        return new ArrayList<>();
    }

    @Override
    public Double viewMerchantByProductId(String productId) {
        List<MerchantProductDTO> merchantByProductId = productProxy.viewMerchantByProductId(productId).stream().collect(Collectors.toList());
        List<Double> priceByProductId = merchantByProductId.stream().map(MerchantProductDTO::getPrice).collect(Collectors.toList());
        return Collections.min(priceByProductId);
    }
}
