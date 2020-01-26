package com.example.ecom.controller.impl;

import java.util.Random;

import com.example.ecom.controller.APIProxy;
import com.example.ecom.controller.ProductProxy;
import com.example.ecom.dto.*;
import com.example.ecom.entity.Product;
import com.example.ecom.services.CategoryServices;
import com.example.ecom.services.ProductServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        productServices.rank(productDTOS);
        return new ResponseEntity<List<ProductDTO>>(productDTOS, HttpStatus.CREATED);
    }

    @GetMapping("/viewProductById/{id}")
    public ResponseEntity<Product_DTO> viewProductById(@PathVariable("id") String id) {
        Product_DTO product = new Product_DTO();
        Product product1 = productServices.viewProductById(id);
        BeanUtils.copyProperties(product1,product);
        product.setMerchantId(productServices.viewMerchantByProductId(id).getMerchantId());
        product.setPrice(productServices.viewMerchantByProductId(id).getPrice());
        product.setStock(productServices.viewMerchantByProductId(id).getStock());
        product.setMerchantName(productServices.viewMerchantByProductId(id).getMerchantName());
        return new ResponseEntity<Product_DTO>(product, HttpStatus.CREATED);
    }
    @GetMapping("/viewProductCartById/{productId}/{merchantId}")
    public ResponseEntity<MerchantProductDTO> viewProductById(@PathVariable("merchantId") String merchantId,@PathVariable("productId") String productId){
        List<MerchantProductDTO> productsDTOS= productProxy.viewMerchantByProductId(productId);
        MerchantProductDTO merchantProductDTO=new MerchantProductDTO();
        for(MerchantProductDTO m:productsDTOS){
            if(m.getProductId().equals(productId)){
                merchantProductDTO=m;
            }
        }
        return new ResponseEntity<MerchantProductDTO>(merchantProductDTO,HttpStatus.CREATED);
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

        List<ProductDTO> productDTOS1=productServices.rank(productDTOS);
        return new ResponseEntity<List<ProductDTO>>(productDTOS1, HttpStatus.CREATED);
    }


    @PostMapping("/productCart")
    public ResponseEntity<List<Product>> getCartProduct(@RequestBody List<String> ids) {
        List<Product> product = new ArrayList<Product>();
        for (String id : ids) {
            Product productDTO1 = productServices.viewProductById(id);
            product.add(productDTO1);
        }
        return new ResponseEntity<List<Product>>(product, HttpStatus.CREATED);
    }
    @GetMapping("/merchantDetail/{productId}")
    public ResponseEntity<MerchantProductDTO> getMerchantProductDto(@PathVariable("productId") String productId)
    {
        MerchantProductDTO merchantProductDTO=productServices.viewMerchantByProductId(productId);

        return new ResponseEntity<MerchantProductDTO>(merchantProductDTO,HttpStatus.CREATED);
    }

    @GetMapping("/productMerchant/{merchantId}")
    public ResponseEntity<List<ProductsDTO>> getProductWithStock(@PathVariable("merchantId") String merchantId)
    {
        List<String> ids=productProxy.viewProduct(merchantId);
        List<ProductsDTO> listProductsDTO=new ArrayList<>();
        for(String id:ids){
            Product product=productServices.viewProductById(id);
            ProductsDTO productsDTO=new ProductsDTO();
            BeanUtils.copyProperties(product,productsDTO);
            MerchantProductDTO merchantProductDTO=productServices.viewMerchantByProductId((String) id);
            productsDTO.setPrice(merchantProductDTO.getPrice());
            productsDTO.setStock(merchantProductDTO.getStock());
            productsDTO.setProductId(merchantProductDTO.getProductId());
            listProductsDTO.add(productsDTO);
        }
        return new ResponseEntity<List<ProductsDTO>>(listProductsDTO,HttpStatus.CREATED);
    }
    private static final String TOPIC="Products";
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;
//    @Autowired
//    KafkaSearch kafkaSearch;
    @Autowired
APIProxy apiProxy;
    @Autowired
    CategoryServices categoryServices;
    @PostMapping("/addProductByMerchant")
    public ResponseEntity<SearchDTO> addProduct(@RequestBody ProductMerchant productMerchant, @RequestHeader HttpHeaders httpHeaders) {
        String userIdHeader = httpHeaders.get("Auth").get(0);
        UserProfile userProfile=apiProxy.getCurrentUser(userIdHeader);
        productMerchant.setMerchantId(userProfile.getId());
        Product product = new Product();
        MerchantDTO merchantDTO = new MerchantDTO();
        SearchDTO searchDTO=new SearchDTO();
        product.setProductName(productMerchant.getProductName());
        product.setProductDescription(productMerchant.getProductDescription());
        product.setProductAttribute(productMerchant.getProductAttribute());
        product.setProductUsp(productMerchant.getProductUsp());
        product.setImageUrl(productMerchant.getImageUrl());
        Random r = new Random();
        Double randomNum =  (r.nextInt((50 - 0) + 1) + 0.0)/10;
        product.setProductRating(randomNum);
        product.setCategoryId(productMerchant.getCategoryId());
        Product productCreated = productServices.save(product);
        searchDTO.setProductId(productCreated.getProductId());
        searchDTO.setProductName(productCreated.getProductName());
        searchDTO.setProductAttribute(productCreated.getProductAttribute());
        searchDTO.setProductUsp(productCreated.getProductUsp());
        searchDTO.setImageUrl(productCreated.getImageUrl());
        searchDTO.setProductRating(randomNum);
        searchDTO.setProductDescription(productMerchant.getProductDescription());
        searchDTO.setCategoryName(categoryServices.getCategoryName(productCreated.getCategoryId()));
        searchDTO.setMerchantId(productMerchant.getMerchantId());
        Double price=productMerchant.getPrice();
        searchDTO.setPrice(price);
        if(price!=0) {
            searchDTO.setWeighted(randomNum / price);
        }
        else{
            searchDTO.setWeighted(0.0);
        }
        merchantDTO.setMerchantId(productMerchant.getMerchantId());
        merchantDTO.setPrice(productMerchant.getPrice());
        merchantDTO.setProductId(productCreated.getProductId());
        merchantDTO.setStock(productMerchant.getStock());
        Boolean flag = false;

        if (productCreated != null) {
            flag = true;
        }
        //kafkaTemplate.send(TOPIC,String.valueOf(productDTO));
        try {
            kafkaTemplate.send(TOPIC, (new ObjectMapper()).writeValueAsString(searchDTO));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        productProxy.editInventory(merchantDTO);
        return new ResponseEntity<SearchDTO>(searchDTO, HttpStatus.CREATED);
    }
    @PostMapping("/addNewProduct")
    public ResponseEntity<SearchDTO> addNewproduct(@RequestBody ProductMerchant productMerchant){
        SearchDTO searchDTO=new SearchDTO();

        Product product = new Product();
        MerchantDTO merchantDTO = new MerchantDTO();
        product.setProductName(productMerchant.getProductName());
        product.setProductDescription(productMerchant.getProductDescription());
        product.setProductAttribute(productMerchant.getProductAttribute());
        product.setProductUsp(productMerchant.getProductUsp());
        product.setImageUrl(productMerchant.getImageUrl());
        Random r = new Random();
        Double randomNum =  (r.nextInt((50 - 0) + 1) + 0.0)/10;
        product.setProductRating(randomNum);
        product.setCategoryId(productMerchant.getCategoryId());
        Product productCreated = productServices.save(product);
        searchDTO.setProductId(productCreated.getProductId());
        searchDTO.setProductName(productCreated.getProductName());
        searchDTO.setProductAttribute(productCreated.getProductAttribute());
        searchDTO.setProductUsp(productCreated.getProductUsp());
        searchDTO.setImageUrl(productCreated.getImageUrl());
        searchDTO.setProductRating(randomNum);
        searchDTO.setProductDescription(productMerchant.getProductDescription());
        searchDTO.setCategoryName(categoryServices.getCategoryName(productCreated.getCategoryId()));
        searchDTO.setMerchantId(productMerchant.getMerchantId());
        Double price=productMerchant.getPrice();
        searchDTO.setPrice(price);
        if(price!=0) {
            searchDTO.setWeighted(randomNum / price);
        }
        else{
            searchDTO.setWeighted(0.0);
        }
        merchantDTO.setMerchantId(productMerchant.getMerchantId());
        merchantDTO.setPrice(productMerchant.getPrice());
        merchantDTO.setProductId(productCreated.getProductId());
        merchantDTO.setStock(productMerchant.getStock());
        Boolean flag = false;

        if (productCreated != null) {
            flag = true;
        }
        //kafkaTemplate.send(TOPIC,String.valueOf(productDTO));
        try {
            kafkaTemplate.send(TOPIC, (new ObjectMapper()).writeValueAsString(searchDTO));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        productProxy.editInventory(merchantDTO);
        return new ResponseEntity<SearchDTO>(searchDTO,HttpStatus.CREATED);
    }
//    @PostMapping("/addAllProduct")
//    public ResponseEntity<List<ProductDTO>> addall(){
//        List<Product> products = productServices.getAllProducts();
//
//    }
//    }kafkaSearch.addProductToKafka(kafkaMessageProducer.send(productMerchant));
//    @GetMapping("/merchantListForProduct/{productId}")
//    public ResponseEntity<List<Product_DTO>> merchantListforProduct(@PathVariable("productId") String productId)
//    {
//        List<Product_DTO> product_dtos=new ArrayList<>();
//        List<MerchantProductDTO> merchantProductDTOS=productProxy.viewMerchantByProductId(productId);
//        BeanUtils.copyProperties(merchantProductDTOS,product_dtos);
//        for(MerchantProductDTO merchantProductDTO:merchantProductDTOS)
//        {
//            Product_DTO product_dto=new Product_DTO();
//            //BeanUtils.copyProperties(merchantProductDTO,product_dto);
//            product_dto.setCategoryId(productServices.viewProductById(merchantProductDTO.getProductId()).getCategoryId());
//            product_dto.setMerchantName(merchantProductDTO.getMerchantName());
//            product_dto.setStock(merchantProductDTO.getStock());
//            product_dto.setPrice(merchantProductDTO.getPrice());
//            product_dto.setMerchantId(merchantProductDTO.getMerchantId());
//            product_dto.setImageUrl(productServices.viewProductById(merchantProductDTO.getProductId()).getImageUrl());
//            product_dto.setProductAttribute(productServices.viewProductById(merchantProductDTO.getProductId()).getProductAttribute());
//            product_dto.setProductDescription(productServices.viewProductById(merchantProductDTO.getProductId()).getProductDescription());
//            product_dto.setProductName(productServices.viewProductById(merchantProductDTO.getProductId()).getProductName());
//            product_dto.setProductRating(productServices.viewProductById(merchantProductDTO.getProductId()).getProductRating());
//            product_dto.setProductUsp(productServices.viewProductById(merchantProductDTO.getProductId()).getProductUsp());
//            product_dto.setProductId(merchantProductDTO.getProductId());
//            product_dtos.add(product_dto);
//        }
//        return new ResponseEntity<List<Product_DTO>>(product_dtos,HttpStatus.CREATED);
//    }
    // productsDTO.setProductName(productServices.viewProductById((String) id).getProductName());
//            productsDTO.setProductDescription(productServices.viewProductById((String) id).getProductDescription());
//            productsDTO.setProductAttribute(productServices.viewProductById((String) id).getProductAttribute());
//            productsDTO.setImageUrl(productServices.viewProductById((String)id).getImageUrl());
//private String productId;
//    private String productName;
//    private String productDescription;
//    private Map productAttribute;
//    private Double productRating;
//    private String productUsp;
//    private String imageUrl;
//    private String categoryId;
}
