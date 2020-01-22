package com.example.ecom.services;


import com.example.ecom.dto.MerchantProductDTO;
import com.example.ecom.dto.ProductDTO;
import com.example.ecom.dto.ProductsDTO;
import com.example.ecom.entity.Product;

import java.util.List;


public interface ProductServices  {
    Product save(Product product);
    Iterable<Product> findAllById(Iterable<String> iterable);
    List<Product> viewProductsByCategoryId(String categoryId);
    Product viewProductById(String id);
    List<Product> getAllProducts();
    MerchantProductDTO viewMerchantByProductId(String productId);
    List<ProductDTO> rank(List<ProductDTO> productDTOS);
}
