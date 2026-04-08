package com.example.proj.domain.mapPin;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MapPinSaveRequestDto {


    //한국이나 tMap 지원범위로 크기 제한
//    @Min(123)
    @NotBlank(message = "좌표값은 필수")
    private Float lon;
    @NotBlank(message = "좌표값은 필수")
    private Float lat;
    
    @NotBlank(message = "제목값이 없음")
    private String title;
    
    private String description;
    
    

}
