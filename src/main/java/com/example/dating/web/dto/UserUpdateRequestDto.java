package com.example.dating.web.dto;


import com.example.dating.domain.user.User;
import com.example.dating.domain.user.profile.DetailProfiles;
import com.example.dating.domain.user.profile.DreamProfiles;
import com.example.dating.domain.user.profile.category.SexType;
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
public class UserUpdateRequestDto {

    private String nickName;

    private String kakaoId;

    private DreamProfiles dreamProfiles;

    private DetailProfiles detailProfiles;

    private SexType sexType;

}
