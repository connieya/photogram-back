package com.cos.photogramstart.handler;

import com.cos.photogramstart.global.response.FailResponse;
import com.cos.photogramstart.global.response.ResponseEnum;
import com.cos.photogramstart.handler.exception.*;
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
    @ExceptionHandler({DuplicateEmailException.class ,
            DuplicateUserNameException.class})
    public FailResponse exceptionResolveToDuplicate(DuplicateException e) {
        log.info("중복 예외 처리 ");
        if (e instanceof DuplicateEmailException) {
            return new FailResponse(ResponseEnum.D1UPLICATE_EMAIL);
        }
        return new FailResponse(ResponseEnum.DUPLICATE_USER_NAME);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({
            UserNotFoundException.class,
            TokenMissingException.class,
            PasswordMisMatchException.class,
    })
    public FailResponse exceptionResolveUnauthorized(BadCredentialsException e) {

        if (e instanceof UserNotFoundException){
            return new FailResponse(ResponseEnum.NOT_FOUND_USER);
        }

        if (e instanceof TokenMissingException) {
            log.warn("토큰 missing 예외 처리");
            return new FailResponse(ResponseEnum.TOKEN_MISSING);
        }
        return new FailResponse(ResponseEnum.PASSWORD_MISMATCH);
    }

    @ExceptionHandler(FileConvertFailException.class)
    public FailResponse exceptionResolveFileFaile(RuntimeException e) {
        return new FailResponse(ResponseEnum.FILE_CONVERT_FAIL);
    }


}
