package com.example.dating.web;

import com.example.dating.config.AuthUser;
import com.example.dating.exception.response.ResponseData;
import com.example.dating.exception.response.ResponseService;
import com.example.dating.exception.response.ResponseStatus;
import com.example.dating.domain.user.User;
import com.example.dating.domain.user.UserRepository;
import com.example.dating.domain.user.profile.DreamProfiles;
import com.example.dating.domain.user.profile.DreamProfilesResource;
import com.example.dating.exception.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/user/dreamProfiles")
public class UserDreamProfilesController {

    private final ResponseService responseService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity getDreamProfile(@AuthUser User user){
        String userEmail = user.getEmail();
        User byEmail = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);

        DreamProfilesResource resource = new DreamProfilesResource(byEmail.getDreamProfiles());
        ResponseData responseData = responseService.create(ResponseStatus.SUCCESS, resource);
        return ResponseEntity.ok(responseData);
    }


    @PutMapping
    public ResponseEntity updateDreamProfile(@RequestBody DreamProfiles dreamProfiles,
                                            @AuthUser User user){

        User byEmail = userRepository.findByEmail(user.getEmail()).orElseThrow(UserNotFoundException::new);
        byEmail.updateDreamProfiles(dreamProfiles);

        DreamProfilesResource resource = new DreamProfilesResource(byEmail.getDreamProfiles());
        ResponseData responseData = responseService.create(ResponseStatus.SUCCESS, resource);
        return ResponseEntity.ok(responseData);
    }




}
