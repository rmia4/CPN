package com.example.proj.domain.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserSaveRequestDto {
    //
    @NotBlank(message = "유저아이디 없음")
    private String userId;

    @NotBlank(message = "유저 이름 없음")
    private String userName;

    @NotBlank(message = "유저 학번 없음")
    private String userNumber;

    private String gender;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,}$",
            message = "비밀번호는 영문, 숫자, 특수문자를 포함해 8자 이상이어야 합니다."
    )
    private String passwd;

    @NotBlank(message = "비밀번호 확인을 입력해주세요.")
    private String passwdConfirm;

}
