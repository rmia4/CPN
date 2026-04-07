package com.example.proj.repository;


import com.example.proj.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {

    CategoryModel findByCategoryName(String categoryName);
}
