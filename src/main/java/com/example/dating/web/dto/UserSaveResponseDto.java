package com.example.dating.web.dto;


import com.example.dating.domain.user.User;
import com.example.dating.domain.user.profile.DetailProfiles;
import com.example.dating.domain.user.profile.DreamProfiles;
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
public class UserSaveResponseDto {
    private String email;
    private String nickName;

    public UserSaveResponseDto(User user){
        this.email = user.getEmail();
        this.nickName = user.getNickName();
    }
}
