package com.example.proj.domain.timeTable;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class TimeTableUpdateDto {
    @NotNull(message = "강의 제목 없음")
    private String title;

    @NotNull(message = "강의 시간정보 없음")
    private List<TimeSlotModel> timeSlots;

    private String color;
    private Long id;
    private String userId;
}
