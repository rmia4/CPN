package com.example.proj.domain.post.comment;


import com.example.proj.domain.post.PostModel;
import com.example.proj.domain.post.PostRepository;
import com.example.proj.domain.user.UserModel;
import com.example.proj.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public void addComment(CommentSaveRequestDto commentDto, Long postId, String userId) {
        CommentModel comment =  new CommentModel();

        UserModel user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new IllegalArgumentException("존재하지 않는 유저: " + userId);
        }

        PostModel post = postRepository.findPostById(postId);
        if (post == null) {
            throw new IllegalArgumentException("존재하지 않는 게시글: " + postId);
        }

        comment.setUser(user);
        comment.setPost(post);
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
