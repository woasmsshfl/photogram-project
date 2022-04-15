package com.cos.photogramstart.domain.likes;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cos.photogramstart.domain.image.Image;
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
// 한 테이블에 여러개의 유니크 설정을 해주기 위한 어노테이션
// 1번유저는 1번 게시물에 좋아요를 여러번 할 수 없어야 한다. 때문에 두개를 유니크로 묶어준다.
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "likes_uk", columnNames = {
                "imageId",
                "userId"
        })
})
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "imageId")
    @ManyToOne
    private Image image; // 하나의 이미지는 여러개의 좋아요를 받을 수 있다. 1:N

    @JoinColumn(name = "userId")
    @ManyToOne
    private User user; // 한명의 유저는 여러개의 좋아요를 할 수 있다. 1:N

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
