package com.example.proj.repository;

import com.example.proj.model.CommentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<CommentModel, Long> {

    List<CommentModel> findAllByPostId(Long postId);
}
