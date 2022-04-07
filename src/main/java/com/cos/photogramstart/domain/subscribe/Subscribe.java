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
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "subscribe_uk", columnNames = {
                "fromUserId",
                "toUserId"
        })
})
public class Subscribe {// 구독을 위한 중간테이블 생성

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @JoinColumn(name = "fromUserId") // 컬럼명을 직접 지정해주는 어노테이션
    @ManyToOne // N:1 관계설정. 자동으로 Table을 생성해줌
    private User fromUser; // 구독하는 유저

    @JoinColumn(name = "toUserId")
    @ManyToOne
    private User toUser; // 구독받는 유저

    private LocalDateTime createDate;

    @PrePersist // DB에 INSERT 되기 직전에 실행되어 현재시간을 입력한다.
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
