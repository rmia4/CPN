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


}
