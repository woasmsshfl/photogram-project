package com.cos.photogramstart.config.oauth;

import java.util.Map;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

// oauth2 원리 =================================================

// 1. 유저가 본인의 정보를 facebook에 전송한다.
// 2. facebook이 유저가 접속하여 요청한 서버로 인증코드를 보내준다.
// 3. 인증코드(페이스북으로 로그인했다는 인증)안에는 
// AccessToken(로그인한 유저의 정보에 접근할 수 있는 권한)이 요청된다.
// 4. AccessToken으로 서버가 페이스북에게 받을 수 있는 정보는 
// scope안에 들어있는 public_profile,email 이다.
// 5. 서버가 이 scope안에 있는 정보를 토대로 강제로 회원가입->로그인을 진행한다.

// oauth2 library를 사용하면 1~4 번까지의 과정이 내부적으로 실행된다!
// 즉,바로 회원가입과 로그인만 진행하면 된다.
// ===========================================================

// oauth2 로그인을 위한 서비스 생성
// 페이스북 로그인 요청이 갔을때 페이스북이 회원정보를 응답하는 공간

@RequiredArgsConstructor
@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override // userRequest안에 facebook이 보낸 회원정보가 담긴다!
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {
        // 현재 메소드가 실행되는지 확인
        // System.out.println("OAuth2 서비스 됨");

        // facebook이 보낸 정보가 담겼는지 확인
        // OAuth2User oAuth2User = super.loadUser(userRequest);
        // System.out.println(oAuth2User.getAttributes());
        // return null;

        OAuth2User oauth2User = super.loadUser(userRequest);
        // System.out.println(oauth2User.getAttributes());

        Map<String, Object> userInfo = oauth2User.getAttributes();

        String username = "facebook_" + (String) userInfo.get("id");
        String password = new BCryptPasswordEncoder()
                .encode(UUID.randomUUID().toString());
        String email = (String) userInfo.get("email");
        String name = (String) userInfo.get("name");

        User userEntity = userRepository.findByUsername(username);

        if (userEntity == null) { // 페이스북으로 최초 로그인시
            User user = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .name(name)
                    .role("ROLE_USER")
                    .build();

            // 회원가입 진행
            return new PrincipalDetails(userRepository.save(user), oauth2User.getAttributes());

        } else { // 이미 회원가입이 된 경우
            return new PrincipalDetails(userEntity, oauth2User.getAttributes());
        }

    }
}
