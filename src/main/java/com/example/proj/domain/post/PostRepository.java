package com.example.proj.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostRepository extends JpaRepository<PostModel, Long> {

    PostModel findPostById(Long id);

    List<PostModel> findAllByCategory_categoryName(String category);

    Page<PostModel> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<PostModel> findAllByCategory_categoryNameOrderByCreatedAtDesc(String category, Pageable pageable);

    Page<PostModel> findAllByTitleStartingWithOrderByCreatedAtDesc(String titlePrefix, Pageable pageable);

    List<PostModel> findAllByUser_Id(Long userId);

    List<PostModel> findAllByCategory_Id(Long categoryId);

    long countByCategory_Id(Long categoryId);

    @EntityGraph(attributePaths = {"category", "images"})
    List<PostModel> findDistinctByLatIsNotNullAndLonIsNotNull();

}
