package com.example.ecom.controller;



import com.example.ecom.dto.MerchantProductDTO;
import com.example.ecom.dto.ProductDTO;
import com.example.ecom.entity.Product;
import com.example.ecom.services.ProductServices;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
@RestController

public class ProductController {
    //private static final ConfigurationUtils BeanUtils = ;
    //    @GetMapping("/view")
//    Double price=@GetMapping("/merchant")
//    public List<Product> getProduct(@RequestBody productId productId){
//        List<Product> list=new List<>();
//        Iterator iterator=list.iterator();
//        while(iterator.hasNext()){
//            if(iterator.next().productId==productId){
//                list.add(iterator.next());
//            }
//        }
//    }
    @Autowired
    ProductServices productServices;
    @ResponseBody
    @PostMapping("/addProduct")
    public ResponseEntity<String> addOrUpdateProduct(@RequestBody ProductDTO productDTO){
        Product product=new Product();
        BeanUtils.copyProperties(productDTO, product);
        Product productCreated=productServices.save(product);
        return new ResponseEntity<String>(productCreated.getProductId(),HttpStatus.CREATED);
    }
    @GetMapping("/viewProductsByCategoryId/{id}")
    public ResponseEntity<List<Product>> viewProductsByCategoryId(@PathVariable("id") String id)
    {
        Product product=new Product();
        List<Product> products=productServices.viewProductsByCategoryId(id);
        return new ResponseEntity<List<Product>>(products,HttpStatus.CREATED);
    }
    @GetMapping("/viewProductById/{id}")
    public ResponseEntity<Product> viewProductById(@PathVariable("id") String id){
        Product product=new Product();
        Product product1=productServices.viewProducById(id);
        return new ResponseEntity<Product>(product1,HttpStatus.CREATED);
    }
    @GetMapping("product/viewAllProduct")
    public ResponseEntity<String> getAllProducts()
    {
        List<Product> products=productServices.getAllProducts();
        return new ResponseEntity(products,HttpStatus.CREATED);
    }
    @GetMapping("product/viewMerchants/{productId}")
    public List<Integer> viewMerchants(@PathVariable("productId") String productId)
    {
        return productServices.viewMerchants(productId);
    }

}
