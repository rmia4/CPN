package com.example.proj.domain.admin;

import com.example.proj.domain.post.PostModel;
import com.example.proj.domain.post.PostRepository;
import com.example.proj.domain.post.category.CategoryModel;
import com.example.proj.domain.post.category.CategoryRepository;
import com.example.proj.domain.user.UserModel;
import com.example.proj.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserModel> findAllUsers() {
        return userRepository.findAll();
    }

    public UserModel findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다: " + id));
    }

    public AdminUserUpdateRequestDto toUpdateDto(UserModel user) {
        AdminUserUpdateRequestDto dto = new AdminUserUpdateRequestDto();
        dto.setUserId(user.getUserId());
        dto.setUserName(user.getUserName());
        dto.setUserNumber(user.getUserNumber());
        dto.setGender(user.getGender());
        dto.setRole(user.getRole());
        dto.setMannerPoint(user.getMannerPoint());
        dto.setStyle1(user.getStyle1());
        dto.setStyle2(user.getStyle2());
        dto.setStyle3(user.getStyle3());
        return dto;
    }

    public UserModel updateUser(Long id, AdminUserUpdateRequestDto dto) {
        UserModel user = findUser(id);

        String userId = dto.getUserId().trim();
        String userNumber = dto.getUserNumber().trim();

        UserModel sameUserId = userRepository.findByUserId(userId);
        if (sameUserId != null && !sameUserId.getId().equals(id)) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        UserModel sameUserNumber = userRepository.findByUserNumber(userNumber);
        if (sameUserNumber != null && !sameUserNumber.getId().equals(id)) {
            throw new IllegalArgumentException("이미 사용 중인 학번입니다.");
        }

        user.setUserId(userId);
        user.setUserName(dto.getUserName().trim());
        user.setUserNumber(userNumber);
        user.setGender(blankToNull(dto.getGender()));
        user.setRole(normalizeRole(dto.getRole()));
        user.setMannerPoint(dto.getMannerPoint() == null ? 36.5f : dto.getMannerPoint());
        user.setStyle1(blankToNull(dto.getStyle1()));
        user.setStyle2(blankToNull(dto.getStyle2()));
        user.setStyle3(blankToNull(dto.getStyle3()));

        if (dto.getPasswd() != null && !dto.getPasswd().isBlank()) {
            user.setPasswd(passwordEncoder.encode(dto.getPasswd()));
        }

        return userRepository.save(user);
    }

    public List<PostModel> findPostsByUser(Long userId) {
        findUser(userId);
        return postRepository.findAllByUser_Id(userId);
    }

    public PostModel findUserPost(Long userId, Long postId) {
        findUser(userId);
        PostModel post = postRepository.findPostById(postId);
        if (post == null || post.getUser() == null || !post.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("해당 사용자가 작성한 게시글이 아닙니다.");
        }
        return post;
    }

    public void deleteUserPost(Long userId, Long postId) {
        findUserPost(userId, postId);
        postRepository.deleteById(postId);
    }

    public List<CategoryModel> findAllCategories() {
        return categoryRepository.findAll();
    }

    public CategoryModel findCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다: " + id));
    }

    public long countPostsByCategory(Long categoryId) {
        return postRepository.countByCategory_Id(categoryId);
    }

    public void addCategory(String categoryName) {
        String name = normalizeCategoryName(categoryName);
        if (categoryRepository.findByCategoryName(name) != null) {
            throw new IllegalArgumentException("이미 존재하는 카테고리입니다.");
        }

        CategoryModel category = new CategoryModel();
        category.setCategoryName(name);
        categoryRepository.save(category);
    }

    public void updateCategory(Long id, String categoryName) {
        CategoryModel category = findCategory(id);
        if (countPostsByCategory(id) > 0) {
            throw new IllegalArgumentException("게시글이 연결된 카테고리는 이름을 수정할 수 없습니다.");
        }

        String name = normalizeCategoryName(categoryName);
        CategoryModel sameCategory = categoryRepository.findByCategoryName(name);
        if (sameCategory != null && !sameCategory.getId().equals(id)) {
            throw new IllegalArgumentException("이미 존재하는 카테고리입니다.");
        }

        category.setCategoryName(name);
        categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        findCategory(id);
        if (countPostsByCategory(id) > 0) {
            throw new IllegalArgumentException("게시글이 연결된 카테고리는 삭제할 수 없습니다.");
        }

        categoryRepository.deleteById(id);
    }

    private String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }

    private String normalizeRole(String role) {
        if ("ROLE_ADMIN".equals(role)) {
            return "ROLE_ADMIN";
        }
        return "ROLE_USER";
    }

    private String normalizeCategoryName(String categoryName) {
        if (categoryName == null || categoryName.isBlank()) {
            throw new IllegalArgumentException("카테고리 이름을 입력해주세요.");
        }
        return categoryName.trim();
    }
}
