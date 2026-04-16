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

    public void addPost(PostSaveRequestDto postDto, List<String> imagePaths) {
        PostModel post =  new PostModel();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setLon(postDto.getLon());
        post.setLat(postDto.getLat());


        // ✅ 카테고리
        CategoryModel category = categoryRepository.findByCategoryName(postDto.getCategory());
        if (category == null) {
            throw new IllegalArgumentException("존재하지 않는 카테고리: " + postDto.getCategory());
        }
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
        postRepository.save(post);
    }

    public List<PostModel> getAllPosts() {
        return postRepository.findAll();

    }

    public PostModel getPostById(Long id) {
        //여기도 예외처리 해야함
        return postRepository.findPostById(id);

    }
    public void  deletePostById(Long id) {
        //여기도 예외처리 해야함
        postRepository.deleteById(id);
    }

    public List<PostModel> getPostsByCategory(String category){
        List<PostModel> postList = postRepository.findAllByCategory_categoryName(category);
        return postList;

    }


}