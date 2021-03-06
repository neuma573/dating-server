package com.example.dating.domain.user;

import com.example.dating.domain.comments.CommentModel;
import com.example.dating.domain.posts.PostModel;
import com.example.dating.domain.user.profile.DetailProfiles;
import com.example.dating.domain.user.profile.DreamProfiles;
import com.example.dating.domain.user.profile.category.SexType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserModel extends RepresentationModel<UserModel> {
    private Long id;
    private String email;
    private String nickName;
    private Long posts;
    private Long comments;
    private DetailProfiles detailProfiles;
    private DreamProfiles dreamProfiles;
    private String kakaoId;
    private SexType sexType;
}
