package com.cos.photogramstart.web.api;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController // 파일이 아닌 Data를 응답하기 위한 컨트롤러 어노테이션
public class UserApiController {

    public final UserService userService;

    @PutMapping("/api/user/{id}")
    public CMRespDto<?> update(@PathVariable int id,
            UserUpdateDto userUpdateDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        // rawPassword에 null이 왜 뜨지?
        // userUpdateDto 정보를 확인해 봐야겠다.
        // System.out.println("*********************************************");
        // System.out.println(userUpdateDto);
        // System.out.println("*********************************************");
        // userUpdateDto 에 모든정보가 null로 담겨온다! 왤까?
        // 데이터를 json으로 받아야 하기 때문에 @RequestBody 어노테이션을 붙혀주어야 한다고한다.
        // 근데도 에러가 뜬다. 뭐지?? 영상에서는 @RequestBody를 안써도
        // contentType: "application/x-www-form-urlencoded; charset=utf-8" 이기 때문에
        // 잘 실행 되는데?
        // 아, 지웠다가 다시 쓰고 저장하니까 됐다..??
        // DB에도 잘 들어갔다.
        User userEntity = userService.회원수정(id, userUpdateDto.toEntity());
        principalDetails.setUser(userEntity); // 세션 정보 변경
        return new CMRespDto<>(1, "회원수정 완료", userEntity);
    }
}
