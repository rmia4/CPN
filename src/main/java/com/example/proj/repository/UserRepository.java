package com.example.proj.repository;

import com.example.proj.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserModel,Integer> {
//    UserModel findByUserName(String userName);
    UserModel findByUserId(String userId);
    List<UserModel> findAll();

    UserModel findById(Long id);
    void deleteById(Long id);



}
