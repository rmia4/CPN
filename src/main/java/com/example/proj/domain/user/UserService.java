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
        user.setGender(userSaveRequestDto.getGender());
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

    public UserModel updateUser(String userId, UserUpdateRequestDto dto) {
        UserModel user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new IllegalArgumentException("존재하지 않는 유저: " + userId);
        }

        user.setUserName(dto.getUserName().trim());
        user.setUserNumber(dto.getUserNumber().trim());
        user.setGender(blankToNull(dto.getGender()));
        user.setStyle1(blankToNull(dto.getStyle1()));
        user.setStyle2(blankToNull(dto.getStyle2()));
        user.setStyle3(blankToNull(dto.getStyle3()));

        return userRepository.save(user);
    }

    private String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value.trim();
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




