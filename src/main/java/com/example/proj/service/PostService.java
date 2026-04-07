package com.example.proj.service;


import com.example.proj.dto.PostSaveRequestDto;
import com.example.proj.model.CategoryModel;
import com.example.proj.model.PostModel;
import com.example.proj.model.UserModel;
import com.example.proj.repository.CategoryRepository;
import com.example.proj.repository.PostRepository;
import com.example.proj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public void addPost(PostSaveRequestDto postDto) {
        PostModel post =  new PostModel();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setLon(postDto.getLon());
        post.setLat(postDto.getLat());


        //해당 카테고리, uesrId가 없을 때 예외처리해야함
        try{
            CategoryModel category = categoryRepository.findByCategoryName(postDto.getCategory());
            post.setCategory(category);
        }
        catch(Exception e){

        }
        try{
            UserModel user = userRepository.findByUserId(postDto.getUserId());   //로그인 기능 없어서 일단 이걸로
            post.setUser(user);
        }
        catch(Exception e){

        }

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


}
