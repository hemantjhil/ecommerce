package com.example.ecom.controller.impl;


import com.example.ecom.dto.CategoryDTO;
import com.example.ecom.entity.Category;
import com.example.ecom.services.CategoryServices;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    CategoryServices categoryServices;

    @ResponseBody
    @PostMapping(value = "/addCategory")
    public ResponseEntity<String> addOrUpdateCategory(@RequestBody CategoryDTO categoryDTO){
        Category category =new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        Category categoryCreated=categoryServices.save(category);
        return new ResponseEntity<String>(categoryCreated.getCategoryId(),HttpStatus.CREATED);
    }
    @GetMapping("category/getAllCategoriesName")
    public ResponseEntity<List<String>> getAllCategoryName(){
        //Category category=new Category();
        List<String> categories=categoryServices.getAllCategories();
        return new ResponseEntity<List<String>>(categories,HttpStatus.CREATED);
    }
    @GetMapping("/category/getAllCategories")
    public ResponseEntity<String> getAllCategories()
    {
        List<Category> categories=categoryServices.getAllCategory();
        return new ResponseEntity(categories,HttpStatus.CREATED);
    }
}
