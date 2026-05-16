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
        for (String categoryName : java.util.List.of("자유", "새내기", "장터", "취업", "동아리", "학사이벤트")) {
            CategoryModel category = new CategoryModel();
            category.setCategoryName(categoryName);
            categoryRepository.save(category);
        }

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
        slot.setStartTime(13);
        slot.setEndTime(16);
        table.addTimeSlot(slot);
        timeTableRepository.save(table);

        TimeTableModel table2 = new TimeTableModel();
        table2.setColor("green");
        table2.setTitle("소프트웨어 공학");
        table2.setUser(user2);
        TimeSlotModel slot2 = new TimeSlotModel();
        slot2.setDay(1);
        slot2.setStartTime(9);
        slot2.setEndTime(10);
        slot2.setPlace("319호");
        table2.addTimeSlot(slot2);
        TimeSlotModel slot3 = new TimeSlotModel();
        slot3.setDay(2);
        slot3.setStartTime(11);
        slot3.setEndTime(13);
        table2.addTimeSlot(slot3);
        timeTableRepository.save(table2);

        for (MainService.DummyPostSeed seed : mainService.getDummyPostSeedList()) {
            PostSaveRequestDto postDto = new PostSaveRequestDto();
            postDto.setTitle(seed.title());
            postDto.setContent(seed.content());
            postDto.setCategory(seed.category());
            postDto.setLat(seed.lat());
            postDto.setLon(seed.lon());
            postDto.setUserId(user2.getUserId());

            postService.addPost(postDto, java.util.List.of(seed.imageUrl()));
        }




    }

}
