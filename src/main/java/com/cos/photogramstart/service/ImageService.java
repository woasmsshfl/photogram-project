package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    // application.yml에 지정된 C:/workspace/springbootwork/upload/ 경로로 지정해주는 어노테이션
    @Value("${file.path}")
    private String uploadFolder;

    public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
        // 클라이언트는 서버에 어떤 파일이름이 저장되어있는지 모르기 때문에 이름이 중복이 생길 수 있다.
        // 그럼 이전 데이터는 중복이 되는데 이를 구분해주는 기술이 필요하다. 그게 UUID이다.
        UUID uuid = UUID.randomUUID();

        // UUID가 유일성을 보장해주지만 수십억분의1 확률로 중복이 생길수가 있다.
        // 따라서 서버에 저장되는 file의 이름에 uuid를 더해준다면 그 확률이 더더욱 낮아지게된다.
        String imageFileName = uuid + "_" + imageUploadDto.getFile().getOriginalFilename();

        System.out.println("이미지 파일 이름 : " + imageFileName);

        // image file을 저장할 경로와 파일명을 imageFilePath에 담기.
        Path imageFilePath = Paths.get(uploadFolder + imageFileName);

        // 통신, IO가 발생할 때 예외가 발생할 수 있기 때문에 try-catch로 묶어주어야 한다.
        try {
            Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
