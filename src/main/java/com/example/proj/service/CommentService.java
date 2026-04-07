package com.example.proj.service;


import com.example.proj.dto.CommentSaveRequestDto;
import com.example.proj.model.CommentModel;
import com.example.proj.model.PostModel;
import com.example.proj.model.UserModel;
import com.example.proj.repository.CommentRepository;
import com.example.proj.repository.PostRepository;
import com.example.proj.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.xml.stream.events.Comment;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public void addComment(CommentSaveRequestDto  commentDto) {
        CommentModel comment =  new CommentModel();
        try{
            UserModel user = userRepository.findByUserId(commentDto.getUserId());
            comment.setUser(user);
        }
        catch(Exception e){

        }
        try{
            PostModel post = postRepository.findPostById(commentDto.getPostId());
            comment.setPost(post);
        }
        catch(Exception e){

        }

        comment.setContent(commentDto.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        commentRepository.save(comment);
    }

    public List<CommentModel> findAllByPostId(Long postId) {
        return commentRepository.findAllByPostId(postId);

    }
    public void deleteCommentById(Long id){
        commentRepository.deleteById(id);
    }

}
