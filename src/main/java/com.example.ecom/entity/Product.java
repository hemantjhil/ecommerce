package com.example.ecom.entity;

import lombok.*;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.Generated;
import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Document(collection = "Product")
public class Product implements Serializable {
    public static final String TABLE_NAME="Product";
    @Id
    private String productId;
    private String productName;
    private String productDescription;
    private Map productAttribute;
    private Double productRating;
    private String productUsp;
    private String imageUrl;
    private String categoryId;
    private double weighted;

}
