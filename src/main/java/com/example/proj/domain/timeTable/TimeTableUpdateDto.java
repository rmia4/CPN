package com.example.proj.domain.timeTable;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TimeTableUpdateDto {
    @NotBlank
    private String title;

    @NotBlank
    private int day;

    @NotBlank
    private int startTime;

    @NotBlank
    private int endTime;

    private String place;
    private String color;
    private String userId;
    private Long id;
}
