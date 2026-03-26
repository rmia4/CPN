package com.example.proj.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false, unique = true)
    private String userNumber;

    @Column(nullable = false)
    private float mannerPoint;

    @Column(nullable = true)
    private String style1;
    @Column(nullable = true)
    private String style2;
    @Column(nullable = true)
    private String style3;


}
