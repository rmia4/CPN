package com.example.proj.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserSaveRequestDto {

    @NotBlank(message = "유저아이디 없음")
    private String userId;

    @NotBlank(message = "유저 이름 없음")
    private String userName;

    @NotBlank(message = "유저 학번 없음")
    private String userNumber;




}
