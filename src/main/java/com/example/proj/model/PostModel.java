package com.example.proj.model;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
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
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private Float lon;
    @Column(nullable = true)
    private Float lat;

// url은 나중에


    ///
    /// ////////// primary Key
    ///
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

