package com.example.proj.domain.post.category;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
public class CategoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String categoryName;

}
