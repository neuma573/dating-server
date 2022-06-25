package com.example.dating.exception.exceptions;

import com.example.dating.exception.high.NotExistParameterException;

public class EmailSigninFailedException extends NotExistParameterException {
    public EmailSigninFailedException(String msg, Throwable t) {
        super(msg, t);
    }

    public EmailSigninFailedException(String msg) {
        super(msg);
    }

    public EmailSigninFailedException() {
        super("회원이 아닙니다");
    }
}