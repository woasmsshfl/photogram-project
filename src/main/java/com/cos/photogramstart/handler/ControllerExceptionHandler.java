package com.cos.photogramstart.handler;

import java.util.Map;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController // 데이터로 리턴하기 위한 어노테이션
@ControllerAdvice // 모든 Exception을 모아주는 어노테이션
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class) // RuntimeException이 발생하는 모든 데이터를 모아줌.
    public String validationException(CustomValidationException e) {
        // CMRespDto와 Script 비교
        // 1. 클라이언트에게 응답할 때에는 Script가 좋다.(클라이언트의 브라우저에게 응답)
        // 2. Ajax 통신, Android 통신을 하게 되면 CMRespDto를 쓸것이다.(개발자에게 응답)
        return Script.back(e.getErrorMap().toString());
    }
}
