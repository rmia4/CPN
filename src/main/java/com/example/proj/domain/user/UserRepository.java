package com.example.proj.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserModel,Integer> {
//    UserModel findByUserName(String userName);
    UserModel findByUserId(String userId);
    List<UserModel> findAll();

    UserModel findById(Long id);


}
