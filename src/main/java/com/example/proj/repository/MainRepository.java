package com.example.proj.repository;

import com.example.proj.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MainRepository extends JpaRepository<UserModel,Long> {
    List<UserModel> findAll();


}
