package com.example.proj.model;


import jakarta.persistence.*;

@Entity
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)

    private String userName;

    @Column(nullable = false, unique = true)
    private String name;
    private String studentNumber;



}
