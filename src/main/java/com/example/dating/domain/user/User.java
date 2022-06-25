package com.example.dating.domain.user;

import com.example.dating.domain.LocalDateTimeEntity;
import com.example.dating.domain.comments.Comment;
import com.example.dating.domain.posts.Post;
import com.example.dating.domain.posts.like.PostLike;
import com.example.dating.domain.user.profile.*;

import com.example.dating.domain.user.profile.category.BodyType;
import com.example.dating.domain.user.profile.category.SexType;
import com.example.dating.web.dto.UserUpdateRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

//@JsonIgnoreProperties(value = {"password"})
//@JsonFilter("UserInfo")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity                                                         // jpa가 자동으로 테이블로 만들기 위해 선언해야됨
@Table(name="user")
public class User extends LocalDateTimeEntity
        implements Comparable<User> {

    @Id                                                         // jpa가 자동으로 테이블을 만들기 위해 선언해야됨
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @Column(nullable = false, unique = true)
    private String email;

    private String nickName;


    @Size(min = 2)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<PostLike> like;

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();


    @Embedded
    private DetailProfiles detailProfiles;

    @Embedded
    private DreamProfiles dreamProfiles;                              // 이상형 프로필

    @Enumerated(EnumType.STRING)
    private SexType SexType;

    private String kakaoId;

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

    @Override
    public int compareTo(User o) {
        return this.getId().compareTo(o.id);
    }



    public User updateMyDetailProfiles(DetailProfiles detailProfiles) {
        this.detailProfiles = detailProfiles;
        return this;
    }

    public User updateDreamProfiles(DreamProfiles dreamProfiles) {
        this.dreamProfiles = dreamProfiles;
        return this;
    }

    public User updateUser(UserUpdateRequestDto userUpdateRequestDto) {
        if (userUpdateRequestDto.getDetailProfiles() != null)
            this.detailProfiles = userUpdateRequestDto.getDetailProfiles();

        if (userUpdateRequestDto.getDreamProfiles() != null)
            this.dreamProfiles = userUpdateRequestDto.getDreamProfiles();

        if (userUpdateRequestDto.getKakaoId() != null)
            this.kakaoId = userUpdateRequestDto.getKakaoId();

        if (userUpdateRequestDto.getNickName() != null)
            this.nickName = userUpdateRequestDto.getNickName();
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
