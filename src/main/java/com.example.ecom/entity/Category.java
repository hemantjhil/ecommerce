package com.example.ecom.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Document(collection = "Category")
@NoArgsConstructor
public class Category implements Serializable {
    public static final String TABLE_NAME="Category";
    @Id
    private String categoryId;
    private String categoryName;



}
