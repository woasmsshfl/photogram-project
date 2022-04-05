package com.cos.photogramstart.web.dto.auth;

import lombok.Data;

@Data // GETTER, SETTER를 생성해주는 어노테이션
public class SignupDto {
    private String username;
    private String password;
    private String email;
    private String name;
}
