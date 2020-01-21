package com.example.ecom.services;


import com.example.ecom.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
public interface CategoryServices  {
    Category save(Category category);
    List<String> getAllCategories();
    List<Category> getAllCategory();
}
