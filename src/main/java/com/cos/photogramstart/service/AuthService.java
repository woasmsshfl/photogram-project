package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service // IoC컨테이너에 등록되면서 트랜잭션 관리를 해주는 어노테이션
public class AuthService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Transactional // 트랜잭션 관리 해주는 어노테이션(write할 때 : INSERT,DELETE,UPDATE)
	public User 회원가입(User user) throws RuntimeException {

		// 회원가입 진행
		// user : 통신을 통해 받은 데이터를 담은 오브젝트
		// userEntity : DB에 있는 데이터를 담은 오브젝트
		String rawPassword = user.getPassword(); // user에 담긴 password가 담긴다.
		String encPassword = bCryptPasswordEncoder.encode(rawPassword); // password를 암호화 해준다.
		user.setPassword(encPassword); // user에 암호화 된 password를 담아준다.
		user.setRole("ROLE_USER"); // 회원가입하는 모든 클라이언트에게 User의 권한을 부여한다.
		// 관리자는 "ROLE_ADMIN"

		User principal = null;
		principal = userRepository.save(user); // 통신을 통해 받은 데이터를 DB에 넣고 DB를 통해서 받은 데이터를 User로 리턴.

		return principal;
	}

	@Transactional // 트랜잭션 관리 해주는 어노테이션(write할 때 : INSERT,DELETE,UPDATE)
	public User 관리자생성(User user) throws RuntimeException {

		// 회원가입 진행
		// user : 통신을 통해 받은 데이터를 담은 오브젝트
		// userEntity : DB에 있는 데이터를 담은 오브젝트
		String rawPassword = user.getPassword(); // user에 담긴 password가 담긴다.
		String encPassword = bCryptPasswordEncoder.encode(rawPassword); // password를 암호화 해준다.
		user.setPassword(encPassword); // user에 암호화 된 password를 담아준다.
		user.setRole("ROLE_USER");// 회원가입하는 모든 클라이언트에게 User의 권한을 부여한다.
		// 관리자는 "ROLE_ADMIN"

		User principal = null;
		principal = userRepository.save(user); // 통신을 통해 받은 데이터를 DB에 넣고 DB를 통해서 받은 데이터를 User로 리턴.

		return principal;
	}
}
