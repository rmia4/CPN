package com.example.proj.service;


import com.example.proj.model.PostModel;
import com.example.proj.model.UserModel;
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


    //여기 인수로 유저 아이디 받아오는 것도 추가해야함
    public void addPost(String title, String content) {
        PostModel post =  new PostModel();
        post.setTitle(title);
        post.setContent(content);
        post.setCreatedAt(LocalDateTime.now());

        UserModel user = userRepository.findByUserId("1");   //로그인 기능 없어서 일단 이걸로
        post.setUser(user);

        postRepository.save(post);

    }

    public List<PostModel> getAllPosts() {
        return postRepository.findAll();

    }

    public PostModel getPostById(Long id) {
        return postRepository.findPostById(id);

    }
    public void  deletePostById(Long id) {
        postRepository.deleteById(id);
    }


}
