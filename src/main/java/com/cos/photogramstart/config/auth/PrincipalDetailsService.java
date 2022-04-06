package com.cos.photogramstart.config.auth;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

// 로그인요청이 들어왔을때 인식해여 IoC컨테이너 내부의 UserDetailsService 가 낚아챈다.
// UserDetailsService가 낚아챈 요청에 담긴 http body의 정보를 이용해 로그인을 진행시킨다.
// PrincipalDetailsService에 @Service 어노테이션을 걸어줌으로써 IoC컨테이너에 존재하던 
// UserDetailsService를 없애고 PrincipalDetailsService가 그 자리를 차지하여 진행한다.
// 그때 진행되는것이 Override된 loadUserByUsername 메서드가 실행된다.

@RequiredArgsConstructor
@Service // IoC등록
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // 1. loadUserByUsername가 username만 받아도 내부 시큐리티가 알아서 판단하여 처리해준다.
    // 따라서 UserDetails 타입으로 리턴만 잘 해주면 된다.
    // 2. 리턴이 잘 되면 자동으로 UserDetails 타입을 세션을 만들어준다.

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User userEntity = userRepository.findByUsername(username);

        if (userEntity == null) {
            return null;
        } else {
            return new PrincipalDetails(userEntity);
        }

    }
}
