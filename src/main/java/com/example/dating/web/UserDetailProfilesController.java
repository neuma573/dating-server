package com.example.dating.web;

import com.example.dating.config.AuthUser;
import com.example.dating.exception.response.ResponseData;
import com.example.dating.exception.response.ResponseService;
import com.example.dating.exception.response.ResponseStatus;
import com.example.dating.domain.user.profile.DetailProfilesResource;
import com.example.dating.domain.user.User;
import com.example.dating.domain.user.UserRepository;
import com.example.dating.domain.user.profile.DetailProfiles;
import com.example.dating.exception.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/user/profiles")
public class UserDetailProfilesController {

    private final ResponseService responseService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity getUserProfile(@AuthUser User user){

        String userEmail = user.getEmail();
        User byEmail = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);

        DetailProfilesResource resource = new DetailProfilesResource(byEmail.getDetailProfiles());
        ResponseData responseData = responseService.create(ResponseStatus.SUCCESS, resource);
        return ResponseEntity.ok(responseData);
    }


    @PutMapping
    public ResponseEntity updateUserProfile(@RequestBody DetailProfiles detailProfiles,
                                            @AuthUser User user,
                                            String ns){

        User byEmail = userRepository.findByEmail(user.getEmail()).orElseThrow(UserNotFoundException::new);
        byEmail.updateMyDetailProfiles(detailProfiles);

        DetailProfilesResource resource = new DetailProfilesResource(byEmail.getDetailProfiles());
        ResponseData responseData = responseService.create(ResponseStatus.SUCCESS, resource);
        return ResponseEntity.ok(responseData);
    }




}
