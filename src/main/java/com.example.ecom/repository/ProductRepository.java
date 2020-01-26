package com.example.ecom.repository;

import com.example.ecom.dto.ProductDTO;
import com.example.ecom.entity.Category;
import com.example.ecom.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
//

public interface ProductRepository extends MongoRepository<Product,String> {

    @Override
    Iterable<Product> findAllById(Iterable<String> iterable);

    List<Product> findByCategoryId(String categoryId);

    Product findByProductId(String id);
}
