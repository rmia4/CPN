package com.example.proj.service;

import com.example.proj.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class MainService {
    private UserRepository userRepository;


    public  MainService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
