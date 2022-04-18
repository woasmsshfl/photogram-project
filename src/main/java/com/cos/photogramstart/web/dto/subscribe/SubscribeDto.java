package com.cos.photogramstart.web.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscribeDto {

	// 구독 정보 모달창 정보

	// 누구를 구독할지를 구분하기 위한 아이디 값
	private int id;

	// 목록에 뜬 유저의 이름을 출력하기 위한 값
	private String username;

	// 목록에 뜬 유저의 프로필사진을 출력하기 위한 값
	private String profileImageUrl;

	// 구독한 상태인지 확인하기 위한 값
	private Integer subscribeState;

	// 목록에 뜬 유저가 로그인 한 유저와 동일인물인지 확인하기 위한 값.
	private Integer equalUserState;
}
