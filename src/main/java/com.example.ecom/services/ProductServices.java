package com.example.ecom.services;


import com.example.ecom.dto.MerchantProductDTO;
import com.example.ecom.entity.Product;

import java.util.List;


public interface ProductServices  {
    public Product save(Product product);
    Iterable<Product> findAllById(Iterable<String> iterable);
    List<Product> viewProductsByCategoryId(String categoryId);
    Product viewProducById(String id);
    List<Product> getAllProducts();
    List<Integer> viewMerchants(String productId);
}
