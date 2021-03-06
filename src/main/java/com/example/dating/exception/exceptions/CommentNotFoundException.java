package com.example.dating.exception.exceptions;


import com.example.dating.exception.high.NotExistURIException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// http status code
// 2xx -> OK
// 4xx -> client error
// 5xx -> server error
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CommentNotFoundException extends NotExistURIException {
    public CommentNotFoundException(String message) {
        super(message);
    }
    public CommentNotFoundException(){
        super("코멘트가 존재하지 않습니다");
    }
}
