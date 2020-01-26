package com.example.ecom.services.impl;

import com.example.ecom.controller.CartProxy;
import com.example.ecom.controller.ProductProxy;
import com.example.ecom.dto.MerchantProductDTO;
import com.example.ecom.dto.ProductDTO;
import com.example.ecom.dto.ProductsDTO;
import com.example.ecom.entity.Product;
import com.example.ecom.repository.ProductRepository;
import com.example.ecom.services.ProductServices;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

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
    @Autowired
    CartProxy cartProxy;
    @Autowired
    ProductServices productServices;
    @Override
    public MerchantProductDTO viewMerchantByProductId(String productId) {
        List<MerchantProductDTO> merchantProductDTO = productProxy.viewMerchantByProductId(productId).stream().collect(Collectors.toList());
        List<MerchantProductDTO> merchantProductDTOS=new ArrayList<>();
        for(MerchantProductDTO m:merchantProductDTO){
            String id=m.getMerchantId();
//            int numberOfProductsMerchantSold=productProxy.viewProductsByMerchantId(id).size();
            int count= productProxy.viewProduct(m.getProductId()).size();
            double rating=productProxy.getRatingByMerchantId(id);
            int stock=productProxy.getStockByPidMid(id,m.getProductId());
            double price=m.getPrice();
            double productRating=productRepository.findByProductId(m.getProductId()).getProductRating();
            //int soldItems= (int) cartProxy.viewProductCount(m.getProductId());
            double weighted=count*rating*stock*productRating/price;
            m.setWeighted(weighted);
            merchantProductDTOS.add(m);
        }
        Collections.sort(merchantProductDTOS);
        MerchantProductDTO merchantProductDTO1=merchantProductDTOS.get(0);
        if(merchantProductDTO1!=null) {
            return merchantProductDTO1;
        }
        else{
            return new MerchantProductDTO();
        }
//        Integer stockmin=merchantProductDTO2.getStock();
//        String merchantIdmin=merchantProductDTO2.getMerchantId();
//


    }
//    @Override
//    public List<MerchantProductDTO> viewMerchantsByProductId(String productId)
//    {
//        List<MerchantProductDTO>
//    }
    @Override
    public List<ProductDTO> rank(List<ProductDTO> productDTOS)
    {
        List<ProductDTO> productDTOS1=new ArrayList<>();
        for (ProductDTO p : productDTOS) {
            ProductDTO productDto = new ProductDTO();
            Double value=0.0;
            if(p.getPrice()!=0) {
                value = p.getProductRating() / p.getPrice();
            }
//            int soldItems= (int) cartProxy.viewProductCount(p.getProductId());
            BeanUtils.copyProperties(p, productDto);
            productDto.setWeighted(value);
            productDTOS1.add(productDto);
        }
        Collections.sort(productDTOS1);
        return productDTOS1;
    }

//    public List<ProductDTO> addAll(List<ProductDTO> productDTOS){
//        List<ProductDTO> productDTOS1=new ArrayList<>();
//        for(ProductDTO p:productDTOS){
//            int count= productProxy.viewProduct(p).size();
//            //double rating=productProxy.getRatingByMerchantId(id);
//            int stock=productProxy.getStockByPidMid(id,m.getProductId());
//            double price=m.getPrice();
//            double productRating=productRepository.findByProductId(m.getProductId()).getProductRating();
//            int soldItems= (int) cartProxy.viewProductCount(m.getProductId());
//            double weighted=count*rating*stock*productRating*soldItems/price;
//        }
//    }

}
