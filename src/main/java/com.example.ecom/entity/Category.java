package com.example.ecom.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Document(collection = "Category")
public class Category implements Serializable {
    public static final String TABLE_NAME="Category";
    private String categoryId;
    private String categoryName;

    public Category() {

    }
}
