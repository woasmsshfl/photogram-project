package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final SubscribeRepository subscribeRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder; // password 암호화

	@Value("${file.path}")
	private String uploadFolder;

	@Transactional
	public User 회원프로필사진변경(int principalId, MultipartFile profileImageFile) {

		UUID uuid = UUID.randomUUID(); // uuid

		// UUID가 유일성을 보장해주지만 수십억분의1 확률로 중복이 생길수가 있다.
		// 따라서 서버에 저장되는 file의 이름에 uuid를 더해준다면 그 확률이 더더욱 낮아지게된다.
		String imageFileName = uuid + "_" + profileImageFile.getOriginalFilename(); // 1.jpg

		System.out.println("이미지 파일이름 : " + imageFileName);

		// image file을 저장할 경로와 파일명을 imageFilePath에 담기.
		Path imageFilePath = Paths.get(uploadFolder + imageFileName);

		// 통신, IO가 발생할 때 예외가 발생할 수 있기 때문에 try-catch로 묶어주어야 한다.
		try {
			Files.write(imageFilePath, profileImageFile.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}

		User userEntity = userRepository.findById(principalId).orElseThrow(() -> {
			// throw -> return 으로 변경
			return new CustomApiException("유저를 찾을 수 없습니다.");
		});
		userEntity.setProfileImageUrl(imageFileName);

		return userEntity;
	} // 더티체킹으로 업데이트 됨.

	@Transactional(readOnly = true)
	public UserProfileDto 회원프로필(int pageUserId, int principalId) {
		UserProfileDto dto = new UserProfileDto();

		// SELECT * FROM image WHERE userId = :userId;
		User userEntity = userRepository.findById(pageUserId).orElseThrow(() -> {
			// throw -> return 으로 변경
			return new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
		});

		dto.setUser(userEntity);
		dto.setPageOwnerState(pageUserId == principalId);
		dto.setImageCount(userEntity.getImages().size());

		int subscribeState = subscribeRepository.mSubscribeState(principalId, pageUserId);
		int subscribeCount = subscribeRepository.mSubscribeCount(pageUserId);

		dto.setSubscribeState(subscribeState == 1);
		dto.setSubscribeCount(subscribeCount);

		// forEach를 사용하여 좋아요카운트까지 받아오는 방법.
		userEntity.getImages().forEach((image) -> {
			image.setLikeCount(image.getLikes().size());
		});

		return dto;
	}

	@Transactional
	public User 회원수정(int id, User user) {

		// 1. 영속화 하기
		// get() : 데이터를 무조건 찾았다. 신경쓰지마라.
		// orElseThrow() : 데이터를 못찾았으니 Exception을 발동시키겠다.
		// User userEntity = userRepository.findById(id).get();
		User userEntity = userRepository.findById(id).orElseThrow(() -> {
			return new CustomValidationApiException("찾을 수 없는 id입니다.");
		});

		// 2. 영속화된 오브젝트를 수정 - 더티체킹 (업데이트 완료)
		userEntity.setName(user.getName());

		// userEntity.setPassword(user.getPassword());
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);

		userEntity.setPassword(encPassword);
		userEntity.setBio(user.getBio());
		userEntity.setWebsite(user.getWebsite());
		userEntity.setPhone(user.getPhone());
		userEntity.setGender(user.getGender());
		return userEntity;
	} // - 더티체킹(업데이트 완료)

}
