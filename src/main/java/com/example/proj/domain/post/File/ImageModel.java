package com.example.proj.domain.post.File;

import com.example.proj.domain.post.PostModel;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class ImageModel {

    @Id
    @GeneratedValue
    private Long id;

    private String imageUrl;

    @ManyToOne
    private PostModel post;
}