package com.cos.photogramstart.domain.subscribe;

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
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "subscribe_uk", columnNames = {
                "fromUserId",
                "toUserId"
        })
})
public class Subscribe {// 구독을 위한 중간테이블 생성

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "fromUserId") // 이렇게 컬럼명 만들어! 니 맘대로 만들지 말고!!
    @ManyToOne
    private User fromUser;

    @JoinColumn(name = "toUserId")
    @ManyToOne
    private User toUser;

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}