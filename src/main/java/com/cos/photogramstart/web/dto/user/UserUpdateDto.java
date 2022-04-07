package com.cos.photogramstart.web.dto.user;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class UserUpdateDto {
    private String name; // 필수
    private String password; // 필수
    private String website;
    private String bio;
    private String phone;
    private String gender;

    // 필수가 아닌 데이터까지 Entity로 만들면 조금 위험함. 코드 수정 필요.
    public User toEntity() { // 들어간 Dto의 데이터를 User로 리턴한다.
        return User.builder() // User안의 bulider안에
                .name(name)
                .password(password) // password와
                .website(website)
                .bio(bio) // name을
                .phone(phone)
                .gender(gender)
                .build(); // 넣는다.
    }
}
