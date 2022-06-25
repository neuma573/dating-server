package com.example.dating.domain.posts;

import com.example.dating.domain.comments.Comment;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostModel extends RepresentationModel<PostModel> {
    private Long id;
    private String content;
    private Long likes;
    private String userEmail;
    private String userNickName;
    private String modifyDate;
    private Long comments;
    private Boolean isLike;
    private Boolean isWriter;

}
