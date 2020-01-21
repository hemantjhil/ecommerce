package com.example.ecom.controller.impl;


import com.example.ecom.controller.ProductProxy;
import com.example.ecom.dto.MerchantProductDTO;
import com.example.ecom.dto.ProductDTO;
import com.example.ecom.dto.ProductsDTO;
import com.example.ecom.entity.Product;
import com.example.ecom.services.ProductServices;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductProxy productProxy;

    @Autowired
    ProductServices productServices;

    @ResponseBody
    @PostMapping("/addProduct")
    public ResponseEntity<String> addOrUpdateProduct(@RequestBody ProductDTO productDTO) {
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        Product productCreated = productServices.save(product);
        return new ResponseEntity<String>(productCreated.getProductId(), HttpStatus.CREATED);
    }

    @GetMapping("/viewProductsByCategoryId/{id}")
    public ResponseEntity<List<ProductDTO>> viewProductsByCategoryId(@PathVariable("id") String id) {

        List<Product> products = productServices.viewProductsByCategoryId(id);
        List<ProductDTO> productDTOS = new ArrayList<>();

        for (Product p : products) {
            ProductDTO productDto = new ProductDTO();

            BeanUtils.copyProperties(p,productDto);
            double price = productServices.viewMerchantByProductId(p.getProductId()).getPrice();
            productDto.setPrice(price);
            productDTOS.add(productDto);

        }

        return new ResponseEntity<List<ProductDTO>>(productDTOS, HttpStatus.CREATED);
    }

    @GetMapping("/viewProductById/{id}")
    public ResponseEntity<Product> viewProductById(@PathVariable("id") String id) {
        Product product = new Product();
        Product product1 = productServices.viewProductById(id);
        return new ResponseEntity<Product>(product1, HttpStatus.CREATED);
    }

    @GetMapping("/viewAllProduct")
    public ResponseEntity<String> getAllProducts() {
        List<Product> products = productServices.getAllProducts();
        return new ResponseEntity(products, HttpStatus.CREATED);
    }


    @GetMapping("/landingPage")
    public ResponseEntity<List<ProductDTO>> viewLandingPageProduct() {

        List<Product> products = productServices.getAllProducts();

        List<ProductDTO> productDTOS = new ArrayList<>();

        for (Product p : products) {
            ProductDTO productDto = new ProductDTO();
            BeanUtils.copyProperties(p, productDto);
            double price = productServices.viewMerchantByProductId(p.getProductId()).getPrice();
            productDto.setPrice(price);
            productDTOS.add(productDto);

        }
        List<ProductDTO> productDTOS1=new ArrayList<>();
        for (ProductDTO p : productDTOS) {
            ProductDTO productDto = new ProductDTO();
            double value = p.getProductRating() / p.getPrice();
            BeanUtils.copyProperties(p, productDto);
            productDto.setWeighted(value);
            productDTOS1.add(productDto);
        }
        //List<ProductDTO> productDTOList=
        Collections.sort(productDTOS1);
        Collections.reverse(productDTOS1);
        return new ResponseEntity<List<ProductDTO>>(productDTOS1, HttpStatus.CREATED);
    }


    @PostMapping("/productCart")
    public ResponseEntity<List<Product>> getCartProduct(@RequestBody List<String> ids) {
        List<Product> product = new ArrayList<>();
        for (String id : ids) {
            Product productDTO1 = productServices.viewProductById(id);
            product.add(productDTO1);
        }
        return new ResponseEntity<List<Product>>(product, HttpStatus.CREATED);
    }
    @GetMapping("/merchantDetail")
    public ResponseEntity<MerchantProductDTO> getMerchantProductDto(@PathVariable("productId") String productId)
    {
        MerchantProductDTO merchantProductDTO=productServices.viewMerchantByProductId(productId);
        return new ResponseEntity<MerchantProductDTO>(merchantProductDTO,HttpStatus.CREATED);
    }

    @GetMapping("/productMerchant/{merchantId}")
    public ResponseEntity<List<ProductsDTO>> getProductWithStock(@PathVariable("merchantId") String merchantId )
    {
        List<String> ids=productProxy.viewProduct(merchantId);
        List<ProductsDTO> listProductsDTO=new ArrayList<>();
        for(Object id:ids){
            ProductsDTO productsDTO=new ProductsDTO();
            MerchantProductDTO merchantProductDTO=productServices.viewMerchantByProductId((String) id);
            productsDTO.setPrice(merchantProductDTO.getPrice());
            productsDTO.setStock(merchantProductDTO.getStock());
            productsDTO.setProductName(productServices.viewProductById((String) id).getProductName());
            productsDTO.setProductDescription(productServices.viewProductById((String) id).getProductDescription());
            productsDTO.setProductAttribute(productServices.viewProductById((String) id).getProductAttribute());
            productsDTO.setImageUrl(productServices.viewProductById((String)id).getImageUrl());
            listProductsDTO.add(productsDTO);
        }
        return new ResponseEntity<List<ProductsDTO>>(listProductsDTO,HttpStatus.CREATED);
    }
}
