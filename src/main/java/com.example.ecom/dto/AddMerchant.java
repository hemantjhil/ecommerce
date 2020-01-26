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
public class AddMerchant {

    private String productName;
    private String productDescription;
    private Map productAttribute;
    private String productUsp;
    private String imageUrl;
    private String categoryId;
    private Double price;
    private int stock;
    private String merchantId;
}
