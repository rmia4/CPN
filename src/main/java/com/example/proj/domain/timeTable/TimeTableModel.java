package com.example.proj.domain.timeTable;


import com.example.proj.domain.user.UserModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Entity
public class TimeTableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private int day;

    @Column
    private int startTime;

    @Column
    private int endTime;

    @Column
    private String place;

    @Column
    private String color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "userId", name = "userId")
    private UserModel user;


}
