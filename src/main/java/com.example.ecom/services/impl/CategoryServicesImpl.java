package com.example.ecom.services.impl;

import com.example.ecom.entity.Category;
import com.example.ecom.repository.CategoryRepository;
import com.example.ecom.services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServicesImpl implements CategoryServices {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category save(Category category){

        return  categoryRepository.insert(category);
    }


    @Override
    public List<String> getAllCategories() {
        List<String> categories = categoryRepository.findAll().stream().map(Category::getCategoryName).collect(Collectors.toList());
        if(null!=categories) return categories;
        return new ArrayList<>();
    }

    @Override
    public List<Category> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        if(null!=categories) return categories;
        return new ArrayList<>();
    }
}
