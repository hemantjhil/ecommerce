package com.example.ecom.repository;

import com.example.ecom.entity.Category;
import com.example.ecom.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//
//@Repository
public interface CategoryRepository extends MongoRepository<Category,String> {
//    @Override
//    List<String> getCategoryName();

}
