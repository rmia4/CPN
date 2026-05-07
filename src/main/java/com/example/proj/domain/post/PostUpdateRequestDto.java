package com.example.proj.domain.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostUpdateRequestDto {
    @NotBlank(message = "제목이 없음")
    private String title;

    @NotBlank(message = "내용이 없음")
    private String content;

    @NotBlank(message = "카테고리가 없음")
    private String category;

    private Float lon;
    private Float lat;
}
