package com.example.proj.domain.post;


import com.example.proj.domain.category.CategoryModel;
import com.example.proj.domain.comment.CommentModel;
import com.example.proj.domain.user.UserModel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class PostModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
//    @CreatedDate    //현제 시간으로 자동 생성
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private Float lon;
    @Column(nullable = true)
    private Float lat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "categoryName",name = "category",nullable = false)
    private CategoryModel category;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "userId", name = "user_id", nullable = false)
    private UserModel user;



    /// /////////////OneToMany
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentModel> commentList;



}

