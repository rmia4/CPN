package com.example.proj.domain.timeTable;

import com.example.proj.domain.user.UserModel;
import com.example.proj.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeTableService {
    private final TimeTableRepository timeTableRepository;
    private final UserRepository userRepository;


    @Transactional
    public void updateTimeTable(TimeTableUpdateDto dto){
        TimeTableModel timeTable;
        // 수정인 경우(Id 입력받음)
        //dirty checking 사용
        if(dto.getId() != null){
            timeTable = timeTableRepository.findById(dto.getId()).get();
        }
        else{
            timeTable = new  TimeTableModel();
        }
        timeTable.setTitle(dto.getTitle());
        timeTable.setStartTime(dto.getStartTime());
        timeTable.setEndTime(dto.getEndTime());
        timeTable.setPlace(dto.getPlace());
//        timeTable.setColor(dto.getColor());
        timeTable.setColor("pink");
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
