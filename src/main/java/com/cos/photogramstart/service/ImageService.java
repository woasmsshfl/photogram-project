package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

	private final ImageRepository imageRepository;

	@Transactional(readOnly = true)
	public List<Image> 인기사진() {
		return imageRepository.mPopular();
	}

	// INSERT할것이 아니기 때문에 readOnly를 걸어준다.
	// 영속성 컨텍스트는 변경 감지를 해서 더티체킹과 flush를 반영한다.
	// 하지만 readOnly는 이 행위들을 하지 않는다.
	@Transactional(readOnly = true)
	public Page<Image> 이미지스토리(int principalId, Pageable pageable) {
		Page<Image> images = imageRepository.mStory(principalId, pageable);

		// 2(cos) 로그인
		// images에 좋아요 상태 담기
		images.forEach((image) -> {
			// image의 likeCount에 like의 크기를 담는다.
			image.setLikeCount(image.getLikes().size());

			image.getLikes().forEach((like) -> {
				// 해당 이미지에 like한 유저를 찾아서 login한 유저가 like한 것인지를 비교해야 한다.
				if (like.getUser().getId() == principalId) {
					image.setLikeState(true);
				}
			});

		});

		return images;
	}

	// 이미지 업로드 파일 경로를 서버 외부에 두는 이유
	// 1. 서버내부의 .java 파일들은 컴파일되어 실행된다.
	// 2. 서버가 실행될때 컴파일된 .java파일들이 target 폴더로 가서 .class로 컴파일 된다.
	// 3. 일반적인 정적인 파일(ex.jpg...)도 taget폴더로 이동한다.(deploy : target폴더로 이동하는것)
	// 4. .java 같은 파일은 용량이 KB단위(deploy 0.01초)인데 .jpg같은 파일은 MB단위(0.2초)이다.
	// 5. 그렇담 서버가 실행될때 .class가 먼저 실행되고 .jpg는 아직 deploy되는 중이 되어버려서
	// 시간차의 문제로 인해 이런 파일들을 요청,응답 하는 시간보다 deploy하는 시간이 더 길어진다.
	// 6. 근데 서버폴더 외부에 있으면 deploy할 필요가 없기 때문에 이런 문제가 생기지 않는다.

	// application.yml에 지정된 C:/workspace/springbootwork/upload/ 경로로 지정해주는 어노테이션
	@Value("${file.path}")
	private String uploadFolder;

	@Transactional
	public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {

		// 클라이언트는 서버에 어떤 파일이름이 저장되어있는지 모르기 때문에 이름이 중복이 생길 수 있다.
		// 그럼 이전 데이터는 중복이 되는데 이를 구분해주는 기술이 필요하다. 그게 UUID이다.
		UUID uuid = UUID.randomUUID();

		// UUID가 유일성을 보장해주지만 수십억분의1 확률로 중복이 생길수가 있다.
		// 따라서 서버에 저장되는 file의 이름에 uuid를 더해준다면 그 확률이 더더욱 낮아지게된다.
		String imageFileName = uuid + "_" + imageUploadDto.getFile().getOriginalFilename(); // 1.jpg
		System.out.println("이미지 파일이름 : " + imageFileName);

		// image file을 저장할 경로와 파일명을 imageFilePath에 담기.
		Path imageFilePath = Paths.get(uploadFolder + imageFileName);

		// 통신, IO가 발생할 때 예외가 발생할 수 있기 때문에 try-catch로 묶어주어야 한다.
		try {
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// DB image Table에 저장
		Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser()); // 5cf6237d-c404-43e5-836b-e55413ed0e49_bag.jpeg
		imageRepository.save(image);
	}
}