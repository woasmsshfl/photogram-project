package com.cos.photogramstart.web.dto.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data // GETTER, SETTER를 생성해주는 어노테이션
public class SignupDto {
	// https://bamdule.tistory.com/35 (@Valid 어노테이션 종류)

// 최소값과 최대값을 지정해주는 어노테이션
 // 공란을 허용하지 않는 어노테이션
	private String username;

	private String password;

	private String email;

	private String name;


	// User.java에서 걸어준 Builder 어노테이션을 적용하기 위한 함수
	public User toEntity() { // 들어간 Dto의 데이터를 User로 리턴한다.
		return User.builder() // User안의 bulider안에
				.username(username) // username과
				.password(password) // password와
				.email(email) // email과
				.name(name) // name을
				.build(); // 넣는다.
	}
}
