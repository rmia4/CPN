package com.example.proj.domain.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryModel> findAll() {
        return categoryRepository.findAll();
    }

    public void addCategory(CategoryModel categoryModel) {
        categoryRepository.save(categoryModel);
    }

    public void deleteCategoryByTitle(String categoryName) {
        CategoryModel categoryModel = categoryRepository.findByCategoryName(categoryName);
        categoryRepository.delete(categoryModel);
    }


}
