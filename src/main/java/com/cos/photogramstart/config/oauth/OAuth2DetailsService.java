package com.cos.photogramstart.config.oauth;

import java.util.Map;
import java.util.UUID;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.nimbusds.openid.connect.sdk.UserInfoRequest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

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
// 다른 OAuth2 로그인 기능 추가하기
// 카카오톡은 OAuth2 에 내장되어있지 않기 때문에 따로 쿼리를 작성해 주어야 한다.

// oauth2 로그인을 위한 서비스 생성
// 페이스북 로그인 요청이 갔을때 페이스북이 회원정보를 응답하는 공간

@RequiredArgsConstructor
@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService {

	private final UserRepository userRepository;

	@Override // userRequest안에 facebook이 보낸 회원정보가 담긴다!
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		// 현재 메소드가 실행되는지 확인
		// System.out.println("OAuth2 서비스 됨");
		System.out.println("AccessTokenValue : " + userRequest.getAccessToken().getTokenValue());

		OAuth2User oauth2User = super.loadUser(userRequest);

		// 레트로핏
		// https://www.googleapis.com/drive/v2/files?access_token=userRequest.getAccessToken().getTokenValue()
		System.out.println("=========================================");
		System.out.println(oauth2User.getAttributes());
		System.out.println("=========================================");

		return processOAuth2User(userRequest, oauth2User);

	}

	private OAuth2User processOAuth2User(
			OAuth2UserRequest userRequest, OAuth2User oauth2User) {
		// System.out.println("=======================================");
		// System.out.println("userRequset : " + userRequest);
		// System.out.println("=======================================");
		// userRequset :
		// org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest@7012a37f

		// facebook이 보낸 정보가 담겼는지 확인
		// OAuth2User oAuth2User = super.loadUser(userRequest);
		// System.out.println(oAuth2User.getAttributes());
		// return null;
		OAuth2UserInfo oAuth2UserInfo = null;

		// System.out.println("=======================================");
		// System.out.println("oauth2User.getAttributes : " +
		// oauth2User.getAttributes());
		// System.out.println("=======================================");
		// oauth2User.getAttributes : {id=4943794309030853, name=박지 수,
		// email=stuuuu108@gmail.com}

		if (userRequest.getClientRegistration().getClientName().equals("Google")) {
			oAuth2UserInfo = new GoogleInfo(oauth2User.getAttributes());
		} else if (userRequest.getClientRegistration().getClientName().equals("Facebook")) {
			oAuth2UserInfo = new FacebookInfo(oauth2User.getAttributes());
		}

		String username = oAuth2UserInfo.getUsername();
		String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());
		String email = oAuth2UserInfo.getEmail();
		String name = oAuth2UserInfo.getName();

		User userEntity = userRepository.findByUsername(username);

		// System.out.println("======================================");
		// System.out.println("userEntity : " + userEntity);
		// System.out.println("======================================");
		// userEntity : User [id=2, username=facebook_4943794309030853,
		// password=$2a$10$M4c201x4d6NllD/F3hQJP.EABwW5ryRzK/kqTBYqchKtWd4ho2Iky,
		// name=박지수, website=null, bio=null, email=stuuuu108@gmail.com, phone=null,
		// gender=null, profileImageUrl=f352a6c9-3c5e-4cde-aa41-27e83ffdc17f_cat7.jpg,
		// role=ROLE_USER, createDate=2022-04-18T21:19:14.982275]

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
		} else { // 페이스북으로 이미 회원가입이 되어 있다는 뜻
			return new PrincipalDetails(userEntity, oauth2User.getAttributes());
		}
	}

}