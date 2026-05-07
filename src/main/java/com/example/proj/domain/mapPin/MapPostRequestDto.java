package com.example.proj.domain.mapPin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MapPostRequestDto {
    @NotNull(message = "좌표값은 필수")
    private Float lon;

    @NotNull(message = "좌표값은 필수")
    private Float lat;

    @NotBlank(message = "제목값이 없음")
    private String title;

    @NotBlank(message = "내용이 없음")
    private String description;

    @NotBlank(message = "카테고리가 없음")
    private String category;
}
