package com.example.dating.domain.user.profile;



import com.example.dating.domain.user.profile.category.BodyType;
import com.example.dating.domain.user.profile.category.LocationCategory;
import com.example.dating.domain.user.profile.category.TallType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Setter
@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class DetailProfiles {

    // 키, 몸무게, 사는곳
    @Enumerated(EnumType.STRING)
    private TallType tallType;

    @Enumerated(EnumType.STRING)
    private BodyType bodyType;

    @Enumerated(EnumType.STRING)
    private LocationCategory locationCategory;

}
