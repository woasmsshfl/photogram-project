package com.cos.photogramstart.handler;

import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController // 데이터로 리턴하기 위한 어노테이션
@ControllerAdvice // 모든 Exception을 모아주는 어노테이션
public class ControllerExceptionHandler {

    // 클라이언트에게 응답할 Exception (JavaScript로 응답)
    @ExceptionHandler(CustomValidationException.class) // RuntimeException이 발생하는 모든 데이터를 모아줌.
    public String validationException(CustomValidationException e) {
        // CMRespDto와 Script 비교
        // 1. 클라이언트에게 응답할 때에는 Script가 좋다.(클라이언트의 브라우저에게 응답)
        // 2. Ajax 통신, Android 통신을 하게 되면 CMRespDto를 쓸것이다.(개발자에게 응답)
        if (e.getErrorMap() == null) {
            return Script.back(e.getMessage());
        } else {
            return Script.back(e.getErrorMap().toString());
        }

    }

    @ExceptionHandler(CustomException.class)
    public String exception(CustomException e) {
        return Script.back(e.getMessage());
    }

    // 개발자에게 응답할 Exception (Object(Data)로 응답)
    @ExceptionHandler(CustomValidationApiException.class) // RuntimeException이 발생하는 모든 데이터를 모아줌.
    public ResponseEntity<?> validationApiException(CustomValidationApiException e) {
        return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), e.getErrorMap()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomApiException.class) // RuntimeException이 발생하는 모든 데이터를 모아줌.
    public ResponseEntity<?> apiException(CustomApiException e) {
        return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), null),
                HttpStatus.BAD_REQUEST);
    }
}
