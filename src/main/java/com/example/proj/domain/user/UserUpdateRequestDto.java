package com.example.proj.domain.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserUpdateRequestDto {
    @NotBlank(message = "유저 이름 없음")
    private String userName;

    @NotBlank(message = "유저 학번 없음")
    private String userNumber;

    private String gender;
    private String style1;
    private String style2;
    private String style3;
}
