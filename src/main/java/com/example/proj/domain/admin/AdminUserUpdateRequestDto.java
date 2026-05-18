package com.example.proj.domain.admin;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AdminUserUpdateRequestDto {
    @NotBlank(message = "아이디를 입력해주세요.")
    private String userId;

    @NotBlank(message = "이름을 입력해주세요.")
    private String userName;

    @NotBlank(message = "학번을 입력해주세요.")
    private String userNumber;

    @NotBlank(message = "권한을 선택해주세요.")
    private String role;

    private String gender;
    private String passwd;
    private Float mannerPoint;
    private String style1;
    private String style2;
    private String style3;
}
