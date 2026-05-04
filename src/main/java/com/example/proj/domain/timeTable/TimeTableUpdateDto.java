package com.example.proj.domain.timeTable;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TimeTableUpdateDto {
    @NotBlank(message = "")
    private String title;

    @NotNull(message = "")
    private int day;

    @NotNull(message = "")
    private int startTime;

    @NotNull(message = "")
    private int endTime;

    private String place;
    private String color;
    private String userId;
    private Long id;
}
