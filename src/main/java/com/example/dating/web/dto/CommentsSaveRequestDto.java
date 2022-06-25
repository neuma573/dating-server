package com.example.dating.web.dto;

import com.example.dating.domain.comments.Comment;
import com.example.dating.domain.posts.Post;
import com.example.dating.domain.user.User;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentsSaveRequestDto {

    @NotEmpty
    private String content;

    public Comment toEntity(User user, Post post){
        return Comment.builder()
                .content(content)
                .user(user)
                .post(post)
                .build();
    }
}
