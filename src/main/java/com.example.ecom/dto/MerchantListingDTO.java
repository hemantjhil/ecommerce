package com.example.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MerchantListingDTO {

    private String productId;
    private String merchantName;
    private Integer price;
    private Integer stock;
    private Double rating;
}
