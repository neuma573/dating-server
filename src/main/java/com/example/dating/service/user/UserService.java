package com.example.dating.service.user;

import com.example.dating.domain.user.User;
import com.example.dating.domain.user.UserAdapter;
import com.example.dating.domain.user.UserRepository;
import com.example.dating.exception.exceptions.UserNotFoundException;
import com.example.dating.exception.high.OverlappingDataException;
import com.example.dating.web.dto.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //loadUserByUserEmail
    @Override
    public UserDetails loadUserByUsername(String userPk) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(userPk)
                .orElseThrow(
                        () -> new UserNotFoundException(String.format("사용자(%s)를 찾을 수 없습니다", userPk))
                );
        return new UserAdapter(user);
    }

    public User join(User user){
        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
        if(byEmail.isPresent()){
            throw new OverlappingDataException("이메일이 중복됩니다");
        }

        return userRepository.save(User.builder()
                .email(user.getEmail())
                .nickName(user.getNickName())
                .kakaoId(user.getKakaoId())
                .detailProfiles(user.getDetailProfiles())
                .dreamProfiles(user.getDreamProfiles())
                .password(passwordEncoder.encode(user.getPassword()))
                .roles(Collections.singletonList("ROLE_USER"))
                .build());
    }

    public User join(UserSaveRequestDto user){
        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
        if(byEmail.isPresent()){
            throw new OverlappingDataException("이메일이 중복됩니다");
        }

        User saveUser = user.toEntity();
        saveUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(saveUser);
        return saveUser;
    }
}
