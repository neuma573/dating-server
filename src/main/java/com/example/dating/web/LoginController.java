package com.example.dating.web;

import com.example.dating.common.DocumentLinkToRef;
import com.example.dating.exception.response.ResponseData;
import com.example.dating.exception.response.ResponseService;
import com.example.dating.config.security.JwtTokenProvider;
import com.example.dating.domain.auth.TokenWrapper;
import com.example.dating.exception.response.ResponseStatus;
import com.example.dating.domain.user.User;
import com.example.dating.domain.user.UserRepository;
import com.example.dating.domain.user.profile.DetailProfiles;
import com.example.dating.domain.user.profile.DreamProfiles;
import com.example.dating.domain.user.profile.category.BodyType;
import com.example.dating.domain.user.profile.category.LocationCategory;
import com.example.dating.domain.user.profile.category.TallType;
import com.example.dating.exception.exceptions.EmailSigninFailedException;
import com.example.dating.service.user.UserService;
import com.example.dating.web.dto.UserSaveRequestDto;
import com.example.dating.web.dto.UserSaveResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1", produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
public class LoginController {


    private final ResponseService responseService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;







    public List<User> getAuthUsers(int size) {
        List<User> users = new ArrayList<>();
        System.out.println("usercount=" + userRepository.findAll().size());
        for (int i = 0; i < size; i++) {
            User build = User.builder()
                    .email("px100" + i + "@naver.com")
                    .password(passwordEncoder.encode("aa1aa1"))
                    .dreamProfiles(dreamProfiles())
                    .detailProfiles(detailProfiles())
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();
            users.add(build);
        }

        userRepository.saveAll(users);
        System.out.println("usercount=" + userRepository.findAll().size());
        return users;
    }

    public DetailProfiles detailProfiles() {
        return DetailProfiles.builder()
                .bodyType(BodyType.SKINNY)
                .tallType(TallType.NORMAL)
                .locationCategory(LocationCategory.BUSAN)
                .build();
    }

    public DreamProfiles dreamProfiles() {
        return DreamProfiles.builder()
                .bodyType(BodyType.SKINNY)
                .locationCategory(LocationCategory.BUSAN)
                .tallType(TallType.SMALL)
                .build();
    }

    @PostMapping(value = "/login")
    public ResponseEntity signin(@RequestBody User reqUser) {

        User user = userRepository.findByEmail(reqUser.getEmail()).orElseThrow(EmailSigninFailedException::new);
        if (!passwordEncoder.matches(reqUser.getPassword(), user.getPassword()))
            throw new EmailSigninFailedException("아이디/비밀번호가 틀립니다.");

        String jwtToken = jwtTokenProvider.createToken(user.getEmail(), user.getRoles());
        TokenWrapper tokenWrapper = TokenWrapper.builder()
                .jwtToken(jwtToken)
                .build();

        ResponseData<TokenWrapper> response = responseService.create(ResponseStatus.SUCCESS, tokenWrapper);
        return ResponseEntity.ok(response);
    }


    @PostMapping(value = "/join")
    public ResponseEntity join(
            @Valid @RequestBody UserSaveRequestDto user,
            Errors errors) {

        if (errors.hasErrors()) {
            ResponseData<Errors> response = responseService.create(ResponseStatus.INVALID_REQUEST_PARAMETER_ERROR, errors);
            return ResponseEntity.badRequest().body(response);
        }


        User joinUser = userService.join(user);
        EntityModel<UserSaveResponseDto> entityModel = EntityModel.of(new UserSaveResponseDto(joinUser));
        entityModel.add(linkTo(LoginController.class).slash("login").withRel("login"));
        entityModel.add(linkTo(DocumentLinkToRef.class).slash("docs/index.html#user-join").withRel("documentation_url"));

        //ResponseData<EntityModel> response = responseService.create(ResponseStatus.SUCCESS, entityModel);

        return ResponseEntity.ok(entityModel);
    }

}