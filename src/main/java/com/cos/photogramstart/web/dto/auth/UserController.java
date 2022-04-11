package com.cos.photogramstart.web.dto.auth;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    // @PathVariable은 바인딩하여 데이터를 받아올 수 있게 해주는 어노테이션이다.
    @GetMapping("/user/{pageUserId}")
    public String profile(@PathVariable int pageUserId, Model model,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        UserProfileDto dto = userService.회원프로필(pageUserId, principalDetails.getUser().getId());
        model.addAttribute("dto", dto);
        return "/user/profile";
    }

    // 세션 저장 경로와 확인
    // 1. 클라이언트가 Post방식으로 서버에 /auth/signin 요청을 하면
    // 서버앞에서 보호해주고있는 시큐리티가 요청을 낚아챈다.
    // 2. 시큐리티가 낚아챈 요청을 PrincipalDetailsService로 넘긴다.
    // 3. PrincipalDetailsService 내부에서 username이 있는지 확인한다.
    // 4. username이 없다면 요청을 무시한다.
    // 5. username이 있다면 리턴 된 PrincipalDetails를 받아서 Authentication이라는 객체에 담는다.
    // 6. Session 내부의 SecurityContextHolder 공간에 데이터를 담는다.

    // 즉, 정상경로는 Session/SecurityContextHolder/Authentication/
    // PrincipalDetails/username 이 된다.

    // 이 경로를 일일히 찾으려면 너무 힘들기 때문에 편의성을 위해 만들어진 어노테이션이 있다.
    // @AuthenticationPrincipal 는 Authentication으로 바로 접근할 수 있게 해준다.
    // @AuthenticationPrincipal 덕분에 principalDetails를 바로 찾아주었다
    @GetMapping("/user/{id}/update")
    public String update(@PathVariable int id,
            @AuthenticationPrincipal PrincipalDetails principalDetails)

    {
        // 1. 어노테이션을 이용하여 바로 찾은 유저 정보(**추천**)
        // System.out.println("어노테이션으로 찾은 유저 정보 : " + principalDetails.getUser());

        // 2. 직접 경로를 작성하여 찾은 유저 정보(**비추천 극혐**)
        // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // PrincipalDetails mPrincipalDetails = (PrincipalDetails) auth.getPrincipal();
        // System.out.println("직접 찾은 유저 정보 : " + mPrincipalDetails.getUser());

        // 모델에 principalDetails가 가지고 있는 유저정보 담기
        // principal : 접근주체. 인증된 사용자의 오브젝트 명으로 주로 쓰인다.
        // model.addAttribute("principal", principalDetails.getUser());

        return "/user/update";
    }
}
