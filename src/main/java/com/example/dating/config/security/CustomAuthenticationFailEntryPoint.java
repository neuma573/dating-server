package com.example.dating.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationFailEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException,
            ServletException {

        String errorMessage = ex.getMessage();

        if(errorMessage.contains("Access token expired")) {
            response.sendRedirect("/exception/expired");
        }else if(errorMessage.contains("Invalid access")){
            response.sendRedirect("/exception/invalid");
        }else{
            response.sendRedirect("/exception/other");
        }
    }
}