package com.cos.photogramstart.handler.ex;

public class CustomApiException extends RuntimeException {

    // 런타임 머신이 객체를 구분할때 사용됨.
    private static final long serialVersionUID = 1L;

    // DI
    // message만 받게 해주는 생성자
    public CustomApiException(String message) {
        super(message);
    }
}
