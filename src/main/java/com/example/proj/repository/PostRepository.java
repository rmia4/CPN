package com.example.proj.repository;

import com.example.proj.model.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<PostModel, Long> {

    PostModel findPostById(Long id);
}
