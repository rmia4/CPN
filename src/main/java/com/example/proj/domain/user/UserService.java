package com.example.proj.domain.user;


import com.example.proj.domain.user.login.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void addUser(UserSaveRequestDto userSaveRequestDto) {
        UserModel user = new UserModel();
        user.setUserId(userSaveRequestDto.getUserId());
        user.setUserName(userSaveRequestDto.getUserName());
        user.setUserNumber(userSaveRequestDto.getUserNumber());
        user.setPasswd(passwordEncoder.encode(userSaveRequestDto.getPasswd()));
        //TODO:관리자 계정은 어떻게 만들지 정하기
        user.setRole("ROLE_USER");

        // 비밀번호를 추가하는 로직이 필요하지만, 현재 DTO에 없으므로 임시로 빈 문자열을 사용합니다.
//        user.setPasswd("temp_password");

        user.setMannerPoint(36.5f); //기본값

        //userId 중복일 경우 나오는 에러 처리 필요
        userRepository.save(user);
    }

    // @Transactional
    public void deleteUserById(Long id) {
        UserModel user =  userRepository.findUserById(id);
        if (user != null) {
            userRepository.delete(user);
        }
    }

    //try catch 사용
    public void deleteUserByUserId(String userId) {
        UserModel user =  userRepository.findByUserId(userId);
        if (user != null) {
            userRepository.delete(user);
        }
    }


    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    public UserModel findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    //spring security에서 자동호출하는 메서드 구현
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findByUserId(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetail(user);
    }
}






