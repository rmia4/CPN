package com.example.proj.domain.notification;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotificationSaveRequestDto {

    @NotBlank(message = "userId는 필수")
    private String userId;

//    @NotBlank(message = "")
    private String title;

    @NotBlank(message ="")
    private String description;


}
