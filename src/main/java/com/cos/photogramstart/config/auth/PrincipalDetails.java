package com.cos.photogramstart.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.cos.photogramstart.domain.user.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

	private static final long serialVersionUID = 1L;

	private User user;
	private Map<String, Object> attributes;
	private boolean oauth = false;

	public PrincipalDetails(User user) {
		this.user = user;
	}

	public PrincipalDetails(User user, Map<String, Object> attributes) {
		this.user = user;
		this.attributes = attributes;
		this.oauth = true;
	}

	// 권한을 가져오는 메서드
	// 권한은 1개가 아닐수도 있기 때문에 Collection 타입으로 리턴한다.
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Collection<GrantedAuthority> collector = new ArrayList<>();

		collector.add(() -> { // 람다식. 함수를 넘겨주기 위한 편의성
			return user.getRole();
		});
		return collector;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	// 아래 기술들은 return이 true일때만 정상적으로 로그인이 된다.
	// =====================================================
	@Override
	public boolean isAccountNonExpired() { // 계정이 만료되지 않았는가?
		return true;
	}

	@Override
	public boolean isAccountNonLocked() { // 계정이 잠기지 않았는가?
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() { // 비밀번호 변경한지 오래되지 않았는가?
		return true;
	}

	@Override
	public boolean isEnabled() { // 계정이 활성화 되어있는가?
		return true;
	}

	// OAuth2User method ======================================
	@Override
	public Map<String, Object> getAttributes() {
		return attributes; // {id:121212, name:쌀, email:ssar@nate.com}
	}

	@Override
	public String getName() {
		return (String) attributes.get("name");
	}

}
