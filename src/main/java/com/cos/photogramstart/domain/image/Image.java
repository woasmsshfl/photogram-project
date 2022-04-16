package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import com.cos.photogramstart.domain.likes.Likes;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    @JsonIgnoreProperties({ "images" })
    @JoinColumn(name = "userId")
    @ManyToOne // 이미지를 SELECT하면 JOIN해서 User정보를 같이 담는다.
    private User user;

    // 이미지 좋아요
    // 이미지를 SELECT 할 때, likes 의 Getter를 호출하면 LAZY로 같이 호출한다.
    @JsonIgnoreProperties({ "image" }) // 무한참조 방지 어노테이션
    @OneToMany(mappedBy = "image")
    private List<Likes> likes;

    // image를 호출할 때 같이 담아갈 like의 상태를 담을 변수 생성.
    @Transient // DB에 culumn이 생성되지 않게 하는 어노테이션
    private boolean likeState;

    @Transient
    private int likeCount;

    // 이미지 댓글
    private String comment;

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
