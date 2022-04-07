package com.cos.photogramstart.web.dto.user;

import javax.validation.constraints.NotBlank;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class UserUpdateDto {
    @NotBlank
    private String name; // 필수
    @NotBlank
    private String password; // 필수
    private String website;
    private String bio;
    private String phone;
    private String gender;

    // 필수가 아닌 데이터까지 Entity로 만들면 조금 위험함. 코드 수정 필요.
    public User toEntity() {
        return User.builder()
                .name(name) // validation check 필요
                .password(password) // validation check 필요
                .website(website)
                .bio(bio)
                .phone(phone)
                .gender(gender)
                .build();
    }
}
