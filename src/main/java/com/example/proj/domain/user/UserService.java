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

    /**
     * 로그인 유효성 검사 및 사용자 정보를 조회합니다.
     * (⚠️ 보안 경고: 실제 운영 환경에서는 BCryptPasswordEncoder를 사용해야 합니다.)
     * @param loginRequestDto 로그인 요청 데이터 (userId, password 포함)
     * @return 로그인 성공 시 사용자 모델, 실패 시 null
     */
    public UserModel loginUser(LoginRequestDto loginRequestDto) {
        // 1. DB에서 ID로 사용자 정보를 찾습니다.
        UserModel user = userRepository.findByUserId(loginRequestDto.getUserId());

        if (user == null) {
            return null; // 사용자 없음
        }

        // 2. 비밀번호 비교 (⚠️ 이 비교는 평문 비교이므로 매우 취약합니다!)
        // 실제로는 user.getPassword()와 loginRequestDto.getPassword()를 인코딩하여 비교해야 합니다.
        if (user.getPasswd() != null && user.getPasswd().equals(loginRequestDto.getPassword())) {
            return user; // 비밀번호가 일치함
        } else {
            return null; // 비밀번호 불일치
        }
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






