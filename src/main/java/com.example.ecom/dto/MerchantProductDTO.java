package com.example.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MerchantProductDTO implements Serializable {

    private String merchantId;
    private String productId;
    private int stock;
    private double price;
}
