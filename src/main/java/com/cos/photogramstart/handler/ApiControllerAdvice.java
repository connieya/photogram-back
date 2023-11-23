package com.cos.photogramstart.handler;

import com.cos.photogramstart.config.baseresponse.FailResponse;
import com.cos.photogramstart.config.baseresponse.ResponseEnum;
import com.cos.photogramstart.handler.exception.DuplicateEmailException;
import com.cos.photogramstart.handler.exception.DuplicateException;
import com.cos.photogramstart.handler.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ApiControllerAdvice {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateException.class)
    public FailResponse exceptionResolveToDuplicate(DuplicateException e) {
        log.info("중복 예외 처리 ");
        if (e instanceof DuplicateEmailException) {
            return new FailResponse(ResponseEnum.D1UPLICATE_EMAIL);
        }
        return new FailResponse(ResponseEnum.DUPLICATE_USER_NAME);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public FailResponse exceptionResolveUnauthorized(BadCredentialsException e) {
        if (e instanceof UserNotFoundException){
            return new FailResponse(ResponseEnum.NOT_FOUND_USER);
        }
        return new FailResponse(ResponseEnum.PASSWORD_MISMATCH);
    }
}
