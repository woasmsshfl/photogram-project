package com.cos.photogramstart.service;

import javax.transaction.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder; // password 암호화

    // SELECT * FROM image WHERE userId = :userId
    public User 회원프로필(int userId) {
        User userEntity = userRepository.findById(userId).orElseThrow(() -> {
            throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
        });

        return userEntity;
    }

    @Transactional
    public User 회원수정(int id, User user) {
        // 1. 영속화 하기
        // get() : 데이터를 무조건 찾았다. 신경쓰지마라.
        // orElseThrow() : 데이터를 못찾았으니 Exception을 발동시키겠다.
        // User userEntity = userRepository.findById(id).get();
        User userEntity = userRepository.findById(id).orElseThrow(() -> {
            return new CustomValidationApiException("찾을 수 없는 id 입니다.");
        });

        // 2. 영속화된 오브젝트를 수정
        userEntity.setName(user.getName());

        // userEntity.setPassword(user.getPassword());
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        userEntity.setPassword(encPassword);

        userEntity.setBio(user.getBio());
        userEntity.setWebsite(user.getWebsite());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());
        return userEntity;
    } // - 더티체킹(업데이트 완료)
}