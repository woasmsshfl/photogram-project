package com.cos.photogramstart.web;

import com.cos.photogramstart.web.dto.auth.SignupDto;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller // IoC컨테이너에 등록, File을 Return하는 컨트롤러.
public class authController {

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
    public String signup(SignupDto signupDto) {
        return "auth/signin"; // 회원가입이 완료 되면 로그인 페이지로 이동.
    }
}
