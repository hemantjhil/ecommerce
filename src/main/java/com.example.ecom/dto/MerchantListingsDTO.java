package com.example.ecom.dto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MerchantListingsDTO {


        private String productId;
        private String merchantName;
        private Integer price;
        private Integer stock;
        private Double rating;
        private String merchantId;

}
