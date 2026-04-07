package com.example.proj.service;


import com.example.proj.dto.UserSaveRequestDto;
import com.example.proj.model.UserModel;
import com.example.proj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void addUser(UserSaveRequestDto userSaveRequestDto) {
        UserModel user = new UserModel();
        user.setUserId(userSaveRequestDto.getUserId());
        user.setUserName(userSaveRequestDto.getUserName());
        user.setUserNumber(userSaveRequestDto.getUserNumber());

        user.setMannerPoint(36.5f); //기본값

        //userId 중복일 경우 나오는 에러 처리 필요
        userRepository.save(user);
    }

//    @Transactional
    public void deleteUserById(Long id) {
        UserModel user =  userRepository.findById(id);
        userRepository.delete(user);

    }

    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    public UserModel findByUserId(String userId) {

        return userRepository.findByUserId(userId);
    }




}
