package com.cos.photogramstart.global.error;

import com.cos.photogramstart.global.error.exception.BusinessException;
import com.cos.photogramstart.handler.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("입력값 유효성 검사 ");
        ErrorResponse response = ErrorResponse.of(ErrorCode.INPUT_VALUE_INVALID, e.getBindingResult());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
//        log.info("enum 타입 불일치 or 잘못된 타입으로 request 객체  자바 객체로 바인딩 실패");
        final ErrorResponse response = ErrorResponse.of(ErrorCode.HTTP_MESSAGE_NOT_READABLE);
        return new ResponseEntity<>(response, BAD_REQUEST);
    }

    @ExceptionHandler
    protected ResponseEntity<?> handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse response = ErrorResponse.of(errorCode, e.getErrors());
        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }


    @ExceptionHandler
    protected ResponseEntity<?> handleBadCredentialsException(BadCredentialsException e) {
        log.warn("로그인 실패 예외 처리 !!!!!!!!!!");
        if (e instanceof UserNotFoundException){
            ErrorResponse response = ErrorResponse.of(ErrorCode.USER_NOT_FOUND);

            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));

        }
        ErrorResponse response = ErrorResponse.of(ErrorCode.PASSWORD_MISMATCH);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

}
