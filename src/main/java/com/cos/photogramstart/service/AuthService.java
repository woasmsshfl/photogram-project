package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service // IoC컨테이너에 등록되면서 트랜잭션 관리를 해주는 어노테이션
public class AuthService {

    private final UserRepository userRepository;

    public User 회원가입(User user) {
        // 회원가입 진행
        // user : 통신을 통해 받은 데이터를 담은 오브젝트
        // userEntity : DB에 있는 데이터를 담은 오브젝트
        User userEntity = userRepository.save(user);
        return userEntity; // 통신을 통해 받은 데이터를 DB에 넣고 DB를 통해서 받은 데이터를 User로 리턴.
    }
}
