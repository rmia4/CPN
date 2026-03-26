package com.example.proj.model;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", nullable = false)
    private PostModel post;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName= "userId", name = "user_id", nullable = false)
    private UserModel user;


}
