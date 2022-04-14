package com.cos.photogramstart.web.api;

import java.util.List;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.CMRespDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Sort;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ImageApiController {

    private final ImageService imageService;

    @GetMapping("/api/image")
    public ResponseEntity<?> imageStory(@AuthenticationPrincipal PrincipalDetails principalDetails,
            @PageableDefault(size = 3) Pageable pageable) {
        // @PageableDefault() 어노테이션은 페이징을 할수있다.
        // 3건씩, id기준으로, DESC(내림차순=최신순) 정렬하여 페이지를 해준다.
        // @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) 같은
        // 조건을 넣으려면
        // 직접 쿼리를 작성해 주어야한다.
        Page<Image> images = imageService.이미지스토리(principalDetails.getUser().getId(), pageable);
        return new ResponseEntity<>(new CMRespDto<>(1, "성공", images), HttpStatus.OK);
    }
}
