package com.example.proj.domain.comment;


import com.example.proj.domain.post.PostModel;
import com.example.proj.domain.user.UserModel;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class CommentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
//    @CreatedDate    //날짜 자동 생성
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private PostModel post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName= "userId", name = "user_id", nullable = false)
    private UserModel user;


}
