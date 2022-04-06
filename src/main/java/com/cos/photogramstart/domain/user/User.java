package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// JPA(Java Persistence API) : 자바로 데이터를 DB에 영구적으로 저장할 수 있는 API를 제공해주는 기능
// ORM : 자바에서 오브젝트를 만들면 데이터베이스에 자동으로 테이블이 만들어지는 기능.

@Builder // authController에서 User에 SignupDto의 데이터를 담기 쉽게 해주는 어노테이션
@AllArgsConstructor // 모든 생성자를 생성해주는 어노테이션
@NoArgsConstructor // 빈 생성자를 생성해주는 어노테이션
@Data // GETTER, SETTER 를 생성해주는 어노테이션
@Entity // DB에 TABLE 생성해주는 어노테이션
public class User {

    // 번호 증가 전략이 데이터베이스를 따라가게 해주는 어노테이션
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id // primary Key를 설정해주는 어노테이션
    private int id;

    // AOP : 관점지향프로그래밍
    // 유효성 검사(전처리 후처리 개념)
    // length : DB까지 가서 확인할 필요 없기 때문에 서버 앞단에서 전처리 된다.
    // unique : DB를 통해서 확인할 수 있는 것이기 때문에 후처리 된다.
    @Column(unique = true, length = 20) // username이 중복허용을 하지 않게 하는 어노테이션
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;

    private String website; // 웹사이트

    private String bio; // 자기소개
    @Column(nullable = false)
    private String email;

    private String phone;

    private String gender;

    private String profileImageUrl; // 사진

    private String role; // 권한

    private LocalDateTime createDate;

    @PrePersist // DB에 INSERT 되기 직전에 실행되어 현재시간을 입력한다.
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}