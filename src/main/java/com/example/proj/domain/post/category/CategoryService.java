package com.example.proj.domain.post.category;

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

    public void addCategory(String name) {
        CategoryModel category = new CategoryModel();
        category.setCategoryName(name);
        categoryRepository.save(category);
    }

}
