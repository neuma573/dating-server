package com.example.dating.web.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostsUpdateRequestDto {
    @NotEmpty
    private String content;
}
