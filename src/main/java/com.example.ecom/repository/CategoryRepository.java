package com.example.ecom.repository;

import com.example.ecom.entity.Category;
import com.example.ecom.entity.Product;
import com.example.ecom.services.CategoryServices;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//
//@Repository
public interface CategoryRepository extends MongoRepository<Category,String> {
    //CategoryServices getByCategoryId(String id);
//    @Override
//    List<String> getCategoryName();
     Category findByCategoryId(String id);
}
