package com.example.proj.domain.comment;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class CommentSaveRequestDto {

    @NotBlank(message = "content가 비어있음")
    private String content;

    @NotBlank(message = "postId가 없음")
    private Long postId;

    @NotBlank(message = "userId가 없음")
    private String userId;


}
