package com.example.proj.domain.post;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter

public class PostSaveRequestDto {

    @NotBlank(message = "제목이 없음")
//    @Size(max = 255, message = "제목은 255자를 초과할 수 없습니다.")
    private String title;

    @NotBlank(message = "내용이 없음")
    private String content;

    @NotBlank(message = "올바르지 않은 userId")
    private String userId;

    @NotBlank
    private String category;



    private Float lon;
    private Float lat;






}