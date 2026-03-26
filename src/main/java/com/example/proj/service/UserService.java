package com.example.proj.service;


import com.example.proj.model.UserModel;
import com.example.proj.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    UserRepository userRepository;
    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(String userId, String userName, String userNumber) {
        UserModel user = new UserModel();
        user.setUserId(userId);
        user.setUserName(userName);
        user.setUserNumber(userNumber);

        user.setMannerPoint(36.5f); //기본값

        userRepository.save(user);
    }

    @Transactional
    public void deleteUserById(long id) {
        userRepository.deleteById(id);

    }

    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    public UserModel findByUserId(String userId) {

        return userRepository.findByUserId(userId);
    }




}
