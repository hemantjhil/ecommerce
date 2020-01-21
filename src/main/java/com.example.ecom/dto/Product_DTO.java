package com.example.ecom.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Product_DTO {
    private String productId;
    private String productName;
    private String productDescription;
    private Map productAttribute;
    private Double productRating;
    private String productUsp;
    private String imageUrl;
    private String categoryId;
    private Double price;
    private Double weighted;
    private String merchantId;
    private int stock;
    private String merchantName;
}
