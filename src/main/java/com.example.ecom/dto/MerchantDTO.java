package com.example.ecom.dto;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Builder
@NoArgsConstructor

public class MerchantDTO {
    private String merchantId;
    private String productId;
    private int stock;
    private Double price;
}
