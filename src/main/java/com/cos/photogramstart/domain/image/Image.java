package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String caption; // 사진 설명

    // 클라이언트가 전송한 사진을 서버 내부 특정 폴더에 저장하기 때문에
    // DB에는 저장된 경로를 INSERT해주게 되므로 URL이라는 명을 사용한다.
    private String postImageUrl;

    @JoinColumn(name = "userId")
    @ManyToOne
    private User user;

    // 이미지 좋아요 업데이트 예정
    // 이미지 댓글 업데이트 예정

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
