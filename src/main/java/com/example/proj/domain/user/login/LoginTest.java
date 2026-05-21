package com.example.proj.domain.user.login;


import com.example.proj.common.MainService;
import com.example.proj.domain.post.PostSaveRequestDto;
import com.example.proj.domain.post.PostService;
import com.example.proj.domain.post.category.CategoryModel;
import com.example.proj.domain.post.category.CategoryRepository;
import com.example.proj.domain.timeTable.TimeSlotModel;
import com.example.proj.domain.timeTable.TimeTableModel;
import com.example.proj.domain.timeTable.TimeTableRepository;
import com.example.proj.domain.user.UserModel;
import com.example.proj.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

//로그인 기능 테스트용 유저 생성 클래스파일
@Component
public class LoginTest implements CommandLineRunner {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder; // 주입
    private final TimeTableRepository  timeTableRepository;
    private final PostService postService;
    private final MainService mainService;

    public LoginTest(UserRepository userRepository, PasswordEncoder passwordEncoder,
                     CategoryRepository categoryRepository, TimeTableRepository timeTableRepository,
                     PostService postService, MainService mainService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.categoryRepository = categoryRepository;
        this.timeTableRepository = timeTableRepository;
        this.postService = postService;
        this.mainService = mainService;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
    }
}
