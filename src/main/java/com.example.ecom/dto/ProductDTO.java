package com.example.ecom.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ProductDTO implements Serializable, Comparable<ProductDTO> {

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


    @Autowired
    ProductDTO productDTO;
    @Override
    public int compareTo(ProductDTO productDTO) {
        return (int) (weighted-productDTO.getWeighted());
    }
}
