package com.example.proj.domain.timeTable;

import com.example.proj.domain.user.UserModel;
import com.example.proj.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeTableService {
    private final TimeTableRepository timeTableRepository;
    private final UserRepository userRepository;

    private final List<String> colorList = new ArrayList<>(Arrays.asList(
            "#FADADD", // 연한 핑크
            "#FFE5B4", // 살구
            "#FFFACD", // 레몬 옐로우
            "#E0F7FA", // 민트 블루
            "#E6E6FA", // 라벤더
            "#D8BFD8", // 연보라
            "#F5F5DC", // 베이지
            "#E0FFE0", // 연한 그린
            "#F0FFF0", // 허니듀
            "#FDFD96"  // 소프트 옐로우
    ));

    @Transactional
    public void updateTimeTable(TimeTableUpdateDto dto){
        TimeTableModel timeTable;
        // 수정인 경우(Id 입력받음)
        //dirty checking 사용
        if(dto.getId() != null){
            timeTable = timeTableRepository.findById(dto.getId()).get();
            timeTable.removeTimeSlot();
        }
        else{
            timeTable = new  TimeTableModel();
            //새로 생성일 경우에만 색상 지정(랜덤하게)
            timeTable.setColor(colorList.get(LocalDateTime.now().getSecond() % colorList.size()));
        }
        timeTable.setTitle(dto.getTitle().trim());

        TimeSlotModel slot;
        for(int i=0;i<dto.getTimeSlots().size();i++){
            slot = new TimeSlotModel();
            slot.setDay(dto.getTimeSlots().get(i).getDay());
            slot.setStartTime(dto.getTimeSlots().get(i).getStartTime());
            slot.setEndTime(dto.getTimeSlots().get(i).getEndTime());

            String place = dto.getTimeSlots().get(i).getPlace();
            if(place!=null){
                slot.setPlace(place);
            }
            timeTable.addTimeSlot(slot);
        }
        UserModel user = userRepository.findByUserId(dto.getUserId());
        if(user == null){
            throw new IllegalArgumentException("존재하지 않는 유저: " +dto.getUserId());
        }
        timeTable.setUser(user);

        timeTableRepository.save(timeTable);

    }

    public List<TimeTableModel>  getAllTimeTable(String userId){
        return timeTableRepository.findAllByUser_userId(userId);
    }

    public void deleteTimeTable(Long id){
        if(timeTableRepository.existsById(id)){
            timeTableRepository.deleteById(id);
        }
        else{
            throw new IllegalArgumentException("올바르지 않은 id" + id);
        }
    }

}
