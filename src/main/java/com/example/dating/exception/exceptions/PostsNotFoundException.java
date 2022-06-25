package com.example.dating.exception.exceptions;


import com.example.dating.exception.high.NotExistDataException;
import com.example.dating.exception.high.NotExistURIException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// http status code
// 2xx -> OK
// 4xx -> client error
// 5xx -> server error
@ResponseStatus(HttpStatus.NOT_FOUND)

public class PostsNotFoundException extends NotExistDataException {
    public PostsNotFoundException(String message) {
        super(message);
    }
    public PostsNotFoundException(){
        super("게시물이 존재하지 않습니다");
    }
}
