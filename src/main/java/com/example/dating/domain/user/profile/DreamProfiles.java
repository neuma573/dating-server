package com.example.dating.domain.user.profile;



import com.example.dating.domain.user.profile.category.BodyType;
import com.example.dating.domain.user.profile.category.LocationCategory;
import com.example.dating.domain.user.profile.category.TallType;
import lombok.*;

import javax.persistence.*;


@Setter
@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class DreamProfiles {

    // 키, 몸무게, 사는곳
    @Enumerated(EnumType.STRING)
    @Column(name = "dream_tall_type")
    private TallType tallType;

    @Enumerated(EnumType.STRING)
    @Column(name = "dream_body_type")
    private BodyType bodyType;

    @Enumerated(EnumType.STRING)
    @Column(name = "dream_locationArea")
    private LocationCategory locationCategory;


}
