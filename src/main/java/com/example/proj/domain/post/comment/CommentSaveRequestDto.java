package com.example.proj.domain.post.comment;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class CommentSaveRequestDto {

    @NotBlank(message = "content가 비어있음")
    private String content;

    // userId는 댓글 작성 폼에서 받지 않고 로그인한 사용자 정보에서 가져올 예정
    //      ModelTestController.java 여기는 로그인 세션 정보가 안넘어가서 return null;로 변경
//    @NotBlank(message = "userId가 없음")
//    private String userId;


}
