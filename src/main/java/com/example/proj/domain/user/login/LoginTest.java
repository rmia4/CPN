package com.example.proj.domain.user.login;


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

//로그인 기능 테스트용 유저 생성 클래스파일
@Component
public class LoginTest implements CommandLineRunner {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder; // 주입
    private final TimeTableRepository  timeTableRepository;

    public LoginTest(UserRepository userRepository, PasswordEncoder passwordEncoder,
                     CategoryRepository categoryRepository,  TimeTableRepository timeTableRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.categoryRepository = categoryRepository;
        this.timeTableRepository = timeTableRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        CategoryModel testCategory = new CategoryModel();
        testCategory.setCategoryName("test");
        categoryRepository.save(testCategory);



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


        //TimeTable 테스트용 더미데이터
        TimeTableModel table = new TimeTableModel();
        table.setColor("pink");
        table.setTitle("일반물리학");
        table.setUser(user2);
        TimeSlotModel slot = new TimeSlotModel();
        slot.setDay(3);
        slot.setStartTime(5);
        slot.setEndTime(8);
        table.addTimeSlot(slot);
        timeTableRepository.save(table);

        TimeTableModel table2 = new TimeTableModel();
        table2.setColor("green");
        table2.setTitle("소프트웨어 공학");
        table2.setUser(user2);
        TimeSlotModel slot2 = new TimeSlotModel();
        slot2.setDay(1);
        slot2.setStartTime(1);
        slot2.setEndTime(2);
        slot2.setPlace("319호");
        table2.addTimeSlot(slot2);
        TimeSlotModel slot3 = new TimeSlotModel();
        slot3.setDay(2);
        slot3.setStartTime(3);
        slot3.setEndTime(5);
        table2.addTimeSlot(slot3);
        timeTableRepository.save(table2);




    }

}
