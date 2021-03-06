package com.cos.photogramstart.web;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ImageController {

	private final ImageService imageService;

	@GetMapping({ "/", "/image/story" })
	public String story() {
		return "image/story";
	}

	// 인기페이지는 ajax를 사용할 것이 아니라서 그냥 controller를 사용한다.
	// api controller는 데이터를 리턴하는 서버.
	// api를 구현하는 경우는 브라우저에서 요청하는게 아닐때 사용한다.
	@GetMapping("/image/popular")
	public String popular(Model model) {

		// api는 데이터를 리턴하는 서버!!
		List<Image> images = imageService.인기사진();
		model.addAttribute("images", images);

		return "image/popular";
	}

	@GetMapping("/image/upload")
	public String upload() {
		return "image/upload";
	}

	@PostMapping("/image")
	public String imageUpload(ImageUploadDto imageUploadDto,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {

		// 코드가 같은 공통기능 처리 부분이 아니기 때문에 ValidationAdvice에서 컨트롤 하지 못한다.
		if (imageUploadDto.getFile().isEmpty()) {
			throw new CustomValidationException("이미지가 첨부되지 않았습니다.", null);
		}

		// Service 를 호출하고
		imageService.사진업로드(imageUploadDto, principalDetails);

		// redirect하여 /user/{userId} 경로로 리턴해준다.
		return "redirect:/user/" + principalDetails.getUser().getId();
	}
}