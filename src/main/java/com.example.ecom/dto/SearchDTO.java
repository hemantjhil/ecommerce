package com.example.ecom.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class SearchDTO implements Serializable, Comparable<SearchDTO>{
    private String productId;
    private String productName;
    private String productDescription;
    private Map productAttribute;
    private Double productRating;
    private String productUsp;
    private String imageUrl;
    private String categoryName;
    private Double price;
    private Double weighted;
    private String merchantId;


    @Override
    public int compareTo(SearchDTO searchDTO) {
        return (int)(weighted-searchDTO.getWeighted());
    }
}
