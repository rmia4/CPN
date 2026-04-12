package com.example.proj.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserModel,Long> {
//    UserModel findByUserName(String userName);
    UserModel findByUserId(String userId);
    List<UserModel> findAll();

    UserModel findUserById(Long id);


}
