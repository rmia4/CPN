package com.example.proj.domain.post.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;
//    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    @GetMapping("/add/{name}")
    public String addForm(@PathVariable("name") String name, Model model) {
        if (categoryRepository.findByCategoryName(name) == null) {

            CategoryModel category = new CategoryModel();
            category.setCategoryName(name);

            categoryService.addCategory(category);
            System.out.println(name + " 카테고리 생성");
        }
        return "redirect:/post/list";
    }

}
