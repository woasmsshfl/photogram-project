package com.cos.photogramstart.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// Security 세팅 : 페이지 접근 권한 설정

@EnableWebSecurity // 해당 파일로 시큐리티를 활성화 시키는 어노테이션
@Configuration // IoC컨테이너로 넣어주는 어노테이션
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO Auto-generated method stub
        // super.configure(http); - 기존 Security가 가지고 있는 기능 활성화/ 비활성화

        http.csrf().disable(); // 시큐리티 CSRF토큰 검사를 비활성화 시키는 기능.

        http.authorizeRequests()
                .antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**") // 이 주소경로로 요청이 들어오면
                .authenticated() // 인증이 필요하고
                .anyRequest() // 인증이 필요없는 모든 요청을
                .permitAll() // 허용한다.
                .and() // 그리고
                .formLogin() // 인증이 필요한 요청은
                .loginPage("/auth/signin") // 로그인페이지 /auth/sginin 로 이동하고
                .defaultSuccessUrl("/"); // 로그인이 정상적으로 되면 메인페이지("/")로 이동한다.
    }

}
