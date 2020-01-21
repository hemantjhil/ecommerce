package com.example.ecom.dto;


import lombok.*;

import java.util.Map;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Data
@Builder
public class ProductsDTO {
    private String productName;
    private String productDescription;
    private Map productAttribute;
    private String imageUrl;
    private int stock;
    private Double price;
}

