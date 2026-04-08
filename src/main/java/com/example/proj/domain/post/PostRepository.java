package com.example.proj.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostRepository extends JpaRepository<PostModel, Long> {

    PostModel findPostById(Long id);

    List<PostModel> findAllByCategory_categoryName(String category);

}
