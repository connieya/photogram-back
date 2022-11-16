package com.cos.photogramstart.handler.ex;

import java.util.Map;

// 구독 (팧로우) 중복 요청에 대한 예외처리
public class CustomApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public CustomApiException(String message) {
        super(message);
    }
}
