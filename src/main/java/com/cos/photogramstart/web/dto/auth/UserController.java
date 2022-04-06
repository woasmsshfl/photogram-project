package com.cos.photogramstart.web.dto.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    // @PathVariable은 바인딩하여 데이터를 받아올 수 있게 해주는 어노테이션이다.
    @GetMapping("/user/{id}")
    public String profile(@PathVariable int id) {
        return "/user/profile";
    }

    @GetMapping("/user/{id}/update")
    public String update(@PathVariable int id) {
        return "/user/update";
    }
}
