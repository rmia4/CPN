package com.example.proj.domain.timeTable;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class TimeSlotModel {
    @Column(nullable = false, name = "day_of_week")
    private int day;
    @Column(nullable = false)
    private int startTime;
    @Column(nullable = false)
    private int endTime;

    private String place;
}
