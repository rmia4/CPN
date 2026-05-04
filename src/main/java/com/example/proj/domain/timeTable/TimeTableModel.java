package com.example.proj.domain.timeTable;


import com.example.proj.domain.user.UserModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class TimeTableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String color;

    @ElementCollection
    @CollectionTable(name = "time_table_slot",joinColumns = @JoinColumn(name = "time_table_id"))
    private List<TimeSlotModel> timeSlotModels = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "userId", name = "userId")
    private UserModel user;

    //데이터 추가를 위한 편의메서드
    public void addTimeSlot(TimeSlotModel TimeSlotModel){
        this.timeSlotModels.add(TimeSlotModel);
    }
    public void  removeTimeSlot(){
        this.timeSlotModels.clear();
    }


}
