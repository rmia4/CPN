package com.example.proj.domain.post;


import com.example.proj.domain.post.File.ImageModel;
import com.example.proj.domain.post.category.CategoryModel;
import com.example.proj.domain.post.category.CategoryRepository;
import com.example.proj.domain.user.UserModel;
import com.example.proj.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public PostModel addPost(PostSaveRequestDto postDto, List<String> imagePaths) {
        PostModel post =  new PostModel();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setLon(postDto.getLon());
        post.setLat(postDto.getLat());


        // ✅ 카테고리
        CategoryModel category = getOrCreateCategory(postDto.getCategory());
        post.setCategory(category);


        // ✅ 유저
        UserModel user = userRepository.findByUserId(postDto.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("존재하지 않는 유저: " + postDto.getUserId());
        }
        post.setUser(user);
        // ⭐ 이미지 여러 개 저장
        List<ImageModel> imageList = new ArrayList<>();

        for (String path : imagePaths) {
            ImageModel img = new ImageModel();
            img.setImageUrl(path);
            img.setPost(post);
            imageList.add(img);
        }

        post.setImages(imageList);
        System.out.println("category: " + postDto.getCategory());
        System.out.println("userId: " + postDto.getUserId());
        return postRepository.save(post);
    }

    public List<PostModel> getAllPosts() {
        return postRepository.findAll();

    }

    public PostModel getPostById(Long id) {
        //여기도 예외처리 해야함
        return postRepository.findPostById(id);

    }

    public void updatePost(Long id, PostUpdateRequestDto dto, String userId) {
        PostModel post = getEditablePost(id, userId);

        post.setTitle(dto.getTitle().trim());
        post.setContent(dto.getContent().trim());
        post.setCategory(getOrCreateCategory(dto.getCategory()));
        post.setLon(dto.getLon());
        post.setLat(dto.getLat());

        postRepository.save(post);
    }

    public void deletePostById(Long id, String userId) {
        getEditablePost(id, userId);
        postRepository.deleteById(id);
    }

    public void deletePostByIdForTest(Long id) {
        postRepository.deleteById(id);
    }

    public List<PostModel> getPostsByCategory(String category){
        List<PostModel> postList = postRepository.findAllByCategory_categoryName(category);
        return postList;

    }

    public List<PostModel> getLocatedPosts() {
        return postRepository.findDistinctByLatIsNotNullAndLonIsNotNull();
    }

    private CategoryModel getOrCreateCategory(String categoryName) {
        CategoryModel category = categoryRepository.findByCategoryName(categoryName);
        if (category != null) {
            return category;
        }

        CategoryModel newCategory = new CategoryModel();
        newCategory.setCategoryName(categoryName);
        return categoryRepository.save(newCategory);
    }

    private PostModel getEditablePost(Long id, String userId) {
        PostModel post = postRepository.findPostById(id);
        if (post == null) {
            throw new IllegalArgumentException("존재하지 않는 게시글: " + id);
        }

        if (post.getUser() == null || !post.getUser().getUserId().equals(userId)) {
            throw new IllegalStateException("게시글 작성자만 수정 또는 삭제할 수 있습니다.");
        }

        return post;
    }


}
