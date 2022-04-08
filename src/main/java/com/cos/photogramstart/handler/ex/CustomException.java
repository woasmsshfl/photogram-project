package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomException extends RuntimeException {

    // 런타임 머신이 객체를 구분할때 사용됨.
    private static final long serialVersionUID = 1L;

    // DI
    public CustomException(String message) {
        super(message);
    }
}
