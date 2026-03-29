package com.example.proj.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class

NotificationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "userId", name = "user_id", nullable = false)
    private UserModel user;

}


