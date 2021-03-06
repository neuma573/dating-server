package com.example.dating.web;

import com.example.dating.exception.exceptions.CAuthenticationEntryPointException;
import com.example.dating.exception.high.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/exception")
public class ExceptionController {

    @RequestMapping(value = "/entrypoint")
    public ResponseEntity entrypointException2() {
        throw new CAuthenticationEntryPointException("권한이 없습니다");
    }


}