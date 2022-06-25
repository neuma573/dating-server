package com.example.dating.web.dto;


import com.example.dating.domain.posts.Post;
import com.example.dating.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Collections;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostsSaveRequestDto {

    @NotEmpty
    private String content;

    public Post toEntity(User user){
        return Post.builder()
                .content(content)
                .comments(Collections.emptyList())
                .like(Collections.emptySet())
                .user(user)
                .build();
    }
}
