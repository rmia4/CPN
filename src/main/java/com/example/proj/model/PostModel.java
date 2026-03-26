package com.example.proj.model;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Date;

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
//    @CreatedDate    //현제 시간으로 자동 생성
    private LocalDateTime createdAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "userId", name = "user_id", nullable = false)
    private UserModel user;



}

