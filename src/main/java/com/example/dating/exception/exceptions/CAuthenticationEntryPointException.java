package com.example.dating.exception.exceptions;


import com.example.dating.exception.high.ServiceAcessDeniedException;

public class CAuthenticationEntryPointException extends ServiceAcessDeniedException {
    public CAuthenticationEntryPointException(String msg, Throwable t) {
        super(msg, t);
    }

    public CAuthenticationEntryPointException(String msg) {
        super(msg);
    }

    public CAuthenticationEntryPointException() {
        super();
    }
}