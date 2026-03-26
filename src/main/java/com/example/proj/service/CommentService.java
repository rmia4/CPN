package com.example.proj.service;


import com.example.proj.model.CommentModel;
import com.example.proj.model.PostModel;
import com.example.proj.model.UserModel;
import com.example.proj.repository.CommentRepository;
import com.example.proj.repository.PostRepository;
import com.example.proj.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    CommentRepository commentRepository;
    UserRepository userRepository;
    PostRepository postRepository;


    CommentService(CommentRepository commentRepository,
                   UserRepository userRepository,PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;

    }

    public void addComment(String content, Long postId, Long userId){
        CommentModel commnet =  new CommentModel();
        UserModel user = userRepository.findById(userId);
        PostModel post = postRepository.findPostById(postId);


        commnet.setContent(content);
        commnet.setUser(user);
        commnet.setPost(post);
        commnet.setCreatedAt(LocalDateTime.now());
        commentRepository.save(commnet);



    }




    public List<CommentModel> findAllByPostId(Long postId) {
        return commentRepository.findAllByPostId(postId);

    }

    public void deleteCommentById(Long id){
        commentRepository.deleteById(id);
    }

}
