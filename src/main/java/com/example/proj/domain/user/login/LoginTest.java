package com.example.proj.domain.user.login;


import com.example.proj.domain.user.UserModel;
import com.example.proj.domain.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//로그인 기능 테스트용 유저 생성 클래스파일
@Component
public class LoginTest implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // 주입

    public LoginTest(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        UserModel user = new UserModel();
        user.setUserName("testUser");
        user.setUserNumber("112233");
        user.setRole("ROLE_USER");

        user.setUserId("11");
        // 평문 "1234"를 BCrypt 단방향 해시로 변환하여 저장
        String rawPassword = "1234";

        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.setPasswd(encodedPassword);

        userRepository.save(user);


        /// //// ADMIN 계정 테스트
        UserModel user2 = new UserModel();
        user2.setUserName("testUser2");
        user2.setUserNumber("1122334");
        user2.setRole("ROLE_ADMIN");
        user2.setUserId("22");
        String rawPassword2 = "1234";
        String encodedPassword2 = passwordEncoder.encode(rawPassword2);
        user2.setPasswd(encodedPassword2);
        userRepository.save(user2);



    }

}
