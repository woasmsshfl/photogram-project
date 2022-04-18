package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.photogramstart.config.oauth.OAuth2DetailsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity // 해당 파일로 시큐리티를 활성화 시키는 어노테이션
@Configuration // IoC컨테이너로 넣어주는 어노테이션 
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	private final OAuth2DetailsService oAuth2DetailsService;
	
	@Bean // SecurityConfig가 IoC컨테이너에 등록될 때, Bean 어노테이션을 읽어서
	// BCryptPasswordEncoder를 return해서 IoC컨테이너에 담아두는 기술.
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
	// 인증 설정하는 메서드
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// super 삭제 - 기존 시큐리티가 가지고 있는 기능이 다 비활성화됨.
		// TODO Auto-generated method stub
		// super.configure(http); - 기존 Security가 가지고 있는 기능 활성화/ 비활성화

		http.csrf().disable(); // 시큐리티 CSRF토큰 검사를 비활성화 시키는 기능.

		http.authorizeRequests() // 이 주소경로로 요청이 들어오면
				.antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**", "/api/**")
				.authenticated() // 인증이 필요하고
				.anyRequest() // 인증이 필요없는 모든 요청을
				.permitAll() // 허용한다.
				.and() // 그리고
				.formLogin() // 인증이 필요한 요청은
				.loginPage("/auth/signin") // 로그인페이지 /auth/sginin 로 이동하고 // GET요청 경로
				// loginProcessingUrl은 스프링시큐리티가 로그인 프로세스를 진행해준다.
				.loginProcessingUrl("/auth/signin")// 로그인후 페이지 // POST요청 경로
				// .failureHandler(null)
				// .successHandler(null)
				.defaultSuccessUrl("/")// 로그인이 정상적으로 되면 메인페이지("/")로 이동한다.
				// .usernameParameter("uid")
				.and()
				.oauth2Login() // form로그인 외에도 oauth로그인도 허용한다.
				.userInfoEndpoint() // oauth2 로그인을 진행하면 최종 응답으로 회원정보를 바로 받겠다.
				.userService(oAuth2DetailsService);
	}
	

}
