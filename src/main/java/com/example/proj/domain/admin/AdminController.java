package com.example.proj.domain.admin;

import com.example.proj.domain.user.UserModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @GetMapping
    public String adminHome(Model model) {
        model.addAttribute("userList", adminService.findAllUsers());
        return "pages/admin/adminTest";
    }

    @GetMapping("/users/{id}/edit")
    public String editUserForm(@PathVariable("id") Long id, Model model) {
        UserModel user = adminService.findUser(id);
        model.addAttribute("user", user);
        model.addAttribute("adminUserUpdateRequestDto", adminService.toUpdateDto(user));
        return "pages/admin/userEdit";
    }

    @PostMapping("/users/{id}/edit")
    public String editUser(@PathVariable("id") Long id,
                           @Valid @ModelAttribute AdminUserUpdateRequestDto adminUserUpdateRequestDto,
                           BindingResult bindingResult,
                           Model model) {
        UserModel user = adminService.findUser(id);

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "pages/admin/userEdit";
        }

        try {
            adminService.updateUser(id, adminUserUpdateRequestDto);
        } catch (IllegalArgumentException e) {
            bindingResult.reject("adminUserUpdateError", e.getMessage());
            model.addAttribute("user", user);
            return "pages/admin/userEdit";
        }

        return "redirect:/admin";
    }

    @GetMapping("/users/{id}/posts")
    public String userPosts(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", adminService.findUser(id));
        model.addAttribute("postList", adminService.findPostsByUser(id));
        return "pages/admin/userPosts";
    }

    @PostMapping("/users/{userId}/posts/{postId}/delete")
    public String deleteUserPost(@PathVariable("userId") Long userId,
                                 @PathVariable("postId") Long postId) {
        adminService.deleteUserPost(userId, postId);
        return "redirect:/admin/users/" + userId + "/posts";
    }

    @GetMapping("/categories")
    public String categories(Model model) {
        model.addAttribute("categoryList", adminService.findAllCategories());
        model.addAttribute("adminService", adminService);
        return "pages/admin/categories";
    }

    @PostMapping("/categories/add")
    public String addCategory(@RequestParam("categoryName") String categoryName, Model model) {
        try {
            adminService.addCategory(categoryName);
        } catch (IllegalArgumentException e) {
            model.addAttribute("categoryError", e.getMessage());
            return categories(model);
        }

        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/{id}/edit")
    public String editCategoryForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("category", adminService.findCategory(id));
        model.addAttribute("postCount", adminService.countPostsByCategory(id));
        return "pages/admin/categoryEdit";
    }

    @PostMapping("/categories/{id}/edit")
    public String editCategory(@PathVariable("id") Long id,
                               @RequestParam("categoryName") String categoryName,
                               Model model) {
        try {
            adminService.updateCategory(id, categoryName);
        } catch (IllegalArgumentException e) {
            model.addAttribute("category", adminService.findCategory(id));
            model.addAttribute("postCount", adminService.countPostsByCategory(id));
            model.addAttribute("categoryError", e.getMessage());
            return "pages/admin/categoryEdit";
        }

        return "redirect:/admin/categories";
    }

    @PostMapping("/categories/{id}/delete")
    public String deleteCategory(@PathVariable("id") Long id, Model model) {
        try {
            adminService.deleteCategory(id);
        } catch (IllegalArgumentException e) {
            model.addAttribute("categoryError", e.getMessage());
            return categories(model);
        }

        return "redirect:/admin/categories";
    }
}
