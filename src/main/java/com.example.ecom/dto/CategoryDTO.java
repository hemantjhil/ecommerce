package com.example.ecom.dto;


import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor

public class CategoryDTO implements Serializable {
    private String categoryId;
    private String categoryName;


}
