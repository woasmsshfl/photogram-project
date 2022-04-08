package com.cos.photogramstart.web.dto.image;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageUploadDto {

    private MultipartFile file; // MultipartFile는 파일을 받을수 있는 타입이다.

    private String caption;
}
