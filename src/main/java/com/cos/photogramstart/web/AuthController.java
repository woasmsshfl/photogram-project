package com.cos.photogramstart.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 3. final이 걸려있는 모든 컨텍스트에 대한 의존성주입을 자동으로 해주는 어노테이션.
@Controller // IoC컨테이너에 등록, File을 Return하는 컨트롤러.
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    // @Autowired // 1. DI(의존성주입)를 자동으로 해주는 방법
    // private AuthService authService;

    private final AuthService authService;

    // 2. 생성자를 직접 만들어 DI(의존성주입)을 해주는 방법
    // public AuthController(AuthService authService) {
    // this.authService = authService;
    // }

    // 1. 로그인 페이지
    @GetMapping("/auth/signin")
    public String signinForm() {
        return "auth/signin";
    }

    // 2. 회원가입 페이지
    @GetMapping("/auth/signup")
    public String signupForm() {
        return "auth/signup";
    }

    // 3. 회원가입 기능
    // 로그인 페이지에서 회원가입 버튼 클릭시 auth/signup으로 가고
    // 회원가입이 정상적으로 완료되면 auth/signin 으로 이동하게 하는게 목적이다.
    // form으로 데이터가 날아오면 key=value(x-www-form-urlencoded)
    @PostMapping("/auth/signup")
    public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) {
        // @ResponseBody 어노테이션이 붙으면 data를 리턴하게 바꿔준다.

        // User <- SignupDto
        User user = signupDto.toEntity();
        // log.info(user.toString());
        authService.회원가입(user);
        // System.out.println(userEntity);
        return "auth/signin"; // 회원가입이 완료 되면 로그인 페이지로 이동.

    }
}
