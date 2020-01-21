package com.example.ecom.services.impl;

import com.example.ecom.controller.ProductProxy;
import com.example.ecom.dto.MerchantProductDTO;
import com.example.ecom.dto.ProductDTO;
import com.example.ecom.dto.ProductsDTO;
import com.example.ecom.entity.Product;
import com.example.ecom.repository.ProductRepository;
import com.example.ecom.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    public Product viewProductById(String id){
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
    public MerchantProductDTO viewMerchantByProductId(String productId) {
        List<MerchantProductDTO> merchantProductDTO = productProxy.viewMerchantByProductId(productId).stream().collect(Collectors.toList());
        MerchantProductDTO merchantProductDTO2=new MerchantProductDTO();
        Double pricemin=100000000.0;
        for(MerchantProductDTO merchantProductDTO1:merchantProductDTO){
            Double price=merchantProductDTO1.getPrice();
            if(price<pricemin){
                merchantProductDTO2=merchantProductDTO1;
            }
        }
//        Integer stockmin=merchantProductDTO2.getStock();
//        String merchantIdmin=merchantProductDTO2.getMerchantId();
//
        return merchantProductDTO2;


    }
//    @Override
//    public List<MerchantProductDTO> viewMerchantsByProductId(String productId)
//    {
//        List<MerchantProductDTO>
//    }

}
