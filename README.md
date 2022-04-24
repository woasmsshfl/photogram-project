# PHOTOGRAM PROJECT
- instagram clone codding 하기
***

### 프론트엔드 기초 세팅 


 <a href="https://github.com/codingspecialist/EaszUp-Springboot-Photogram-Start"> GITHUB로 가기 </a>

- 위 링크에서 git clone 하여 프론드엔드 기반 데이터를 받았습니다.
- 코드 참고 : https://github.com/codingspecialist
- 영상 참고 : https://easyupclass.e-itwill.com/course/course_view.jsp?id=27&rtype=0&ch=course

***
### 의존성(Plug-in)

- Spring Boot Version

```xml
 <parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>2.6.6</version>
  <relativePath />
 </parent>
 ```

- Spring Boot DevTools

```xml
  <dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-devtools</artifactId>
   <scope>runtime</scope>
   <optional>true</optional>
  </dependency>
```


- Lombok

```xml
  <dependency>
   <groupId>org.projectlombok</groupId>
   <artifactId>lombok</artifactId>
   <optional>true</optional>
  </dependency>
```

- Spring Data JPA

```xml
  <dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-data-jpa</artifactId>
  </dependency>
```

- JSP Template Engine

```xml
<dependency>
 <groupId>org.apache.tomcat</groupId>
 <artifactId>tomcat-jasper</artifactId>
 <version>9.0.43</version>
</dependency>
```

- JSTL(JSP Tag Library)

```xml
<dependency>
 <groupId>javax.servlet</groupId>
 <artifactId>jstl</artifactId>
</dependency>
```

- MariaDB Driver

```xml
  <dependency>
   <groupId>org.mariadb.jdbc</groupId>
   <artifactId>mariadb-java-client</artifactId>
   <scope>runtime</scope>
  </dependency>
```

- Spring Security

```xml
  <dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-security</artifactId>
  </dependency>
```

- Spring Validation

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
    <version>2.5.2</version>
</dependency>
```

- Spring Web

```xml
  <dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
```

- oauth2-client

```xml
  <dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-oauth2-client</artifactId>
  </dependency>
```

- AOP

```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-aop</artifactId>
      <version>2.4.5</version>
  </dependency>
```
***
### 태그라이브러리

```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
```
***
### OAuth2 개발자 센터


<a href="https://developers.facebook.com/" title="페이스북 개발자 센터로 이동" target="_blank"> - 페이스북 개발자 센터로 이동</a>
<br/>
<a href="https://console.cloud.google.com/getting-started?hl=ko" title="구글 개발자 센터로 이동" target="_blank"> - 구글 개발자 센터로 이동</a>
<br/>
<a href="https://developers.kakao.com/" title="카카오 개발자 센터로 이동" target="_blank"> - 카카오 개발자 센터로 이동</a>
<br/>
<a href="https://developers.naver.com/main/" title="네이버 개발자 센터로 이동" target="_blank"> - 네이버 개발자 센터로 이동</a>

***
### 데이터베이스(Maria DB 10.6ver)

```sql
-- 유저 생성, 권한 부여, 데이터베이스 생성 쿼리
create user 'cos'@'%' identified by 'cos1234';
GRANT ALL PRIVILEGES ON *.* TO 'cos'@'%';
create database photogram;

-- 페이지 쿼리
SELECT * 
FROM image 
WHERE userId 
IN (SELECT toUserId 
FROM subscribe 
WHERE fromUserId = :principalId) 
ORDER BY id DESC;

-- 인기페이지 쿼리
SELECT i.* 
FROM image i 
INNER JOIN 
(
  SELECT imageId, COUNT(imageId) likeCount 
  FROM likes 
  GROUP BY imageId
) c 
ON i.id = c.imageId 
ORDER BY likeCount DESC;

-- 구독하기 쿼리
INSERT INTO subscribe(fromUserId, toUserId, createDate) 
VALUES(:fromUserId, :toUserId, now());

-- 구독취소하기 쿼리
DELETE FROM subscribe 
WHERE fromUserId = :fromUserId AND toUserId = :toUserId;

-- 구독상태확인 쿼리
SELECT COUNT(*) 
FROM subscribe 
WHERE fromUserId = :principalId AND toUserId = :pageUserId;

-- 구독자 수 확인 쿼리
SELECT COUNT(*) 
FROM subscribe 
WHERE fromUserId = :pageUserId;

-- 구독 여부 확인 쿼리
SELECT u.id, u.username, u.profileImageUrl, 
(
  SELECT TRUE 
  FROM subscribe 
  WHERE fromUserId = 1 AND toUserId = u.id
) subscribeState
FROM user u 
INNER JOIN subscribe s
ON u.id = s.toUserId
WHERE s.fromUserId = 2;

-- 구독페이지=구독유저 동일한지 확인하는 쿼리
SELECT u.id, u.username, u.profileImageUrl, 
if((SELECT 1 FROM subscribe 
WHERE fromUserId = 1 AND toUserId = u.id),1,0) subscribeState,
if((1=u.id),1,0) equalUserState
FROM user u INNER JOIN subscribe s
ON u.id = s.toUserId
WHERE s.fromUserId = 2;
```
***
### yml 설정

```yml
server:
  port: 8080
  servlet:
    context-path: /
    encoding:
      charset: utf-8
      enabled: true

spring:
  mvc:
    view:
      prefix: /WEB-INF/views/
      suffix: .jsp

  datasource:
    driver-class-name: org.mariadb.jdbc.Driver
    url: jdbc:mariadb://localhost:3306/photogram?serverTimezone=Asia/Seoul
    username: cos
    password: cos1234

  jpa:
    open-in-view: true
    hibernate:
      ddl-auto: update # create, update, none
      naming:
        physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
    show-sql: true

  output:
    ansi:
      enabled: always

  servlet:
    multipart:
      enabled: true
      max-file-size: 2MB

  security:
    user:
      name: test
      password: 1234

file:
  path: C:/workspace/springbootwork/upload/
```
***

### 기능구현

- 회원가입

![회원가입](https://user-images.githubusercontent.com/97711667/164967227-bf6b031a-a144-4057-9d21-a8deee30d878.gif)

- 로그인
![로그인](https://user-images.githubusercontent.com/97711667/164967221-71be9e4d-34cd-4529-a452-3813721ad5be.gif)

- OAuth2 페이스북 로그인
![페이스북로그인](https://user-images.githubusercontent.com/97711667/164967226-9d012efc-ee02-4b8a-a16c-b943158a4f10.gif)

- OAuth2 구글 로그인
![구글로그인](https://user-images.githubusercontent.com/97711667/164967229-cada9bf9-0f8f-4430-8886-7460f90c0597.gif)

- OAuth2 카카오 로그인
![카카오로그인](https://user-images.githubusercontent.com/97711667/164967224-ed054bd4-e48b-4cf4-9e4a-d14fb71a0ba5.gif)

- OAuth2 네이버 로그인
![네이버로그인](https://user-images.githubusercontent.com/97711667/164967217-2e9c3734-5979-4dbb-870d-38cd92ee3ffe.gif)

- 로그아웃
![로그아웃](https://user-images.githubusercontent.com/97711667/164967259-ba9c78db-25aa-4003-aefc-2daeee445637.gif)

- 프로필 사진 변경
![프로필사진변경](https://user-images.githubusercontent.com/97711667/164967256-905a0a8c-7d87-4f3e-9172-841d149770c6.gif)

- 회원정보 변경
![회원정보변경](https://user-images.githubusercontent.com/97711667/164967257-6b569b56-49d1-45db-b1fa-88b93b039175.gif)

- 사진 올리기
![사진올리기](https://user-images.githubusercontent.com/97711667/164967261-7ebefdeb-b650-4a15-81c9-94d4da924393.gif)

- 구독하기
![구독기능](https://user-images.githubusercontent.com/97711667/164967313-95f7a57d-2031-43ee-b005-8aacc0614b65.gif)

- 댓글 쓰기
![댓글쓰기](https://user-images.githubusercontent.com/97711667/164967300-479cca08-8ef6-4650-a28c-aabf0de1430d.gif)

- 댓글 삭제
![댓글삭제](https://user-images.githubusercontent.com/97711667/164967299-ee194410-a77d-4456-abe3-8e4bedb31898.gif)

- 사진 좋아요
![좋아요](https://user-images.githubusercontent.com/97711667/164967695-ca5b4935-c14e-464f-8f75-a75198eab7be.gif)

- 스크롤 페이징
![스크롤페이징](https://user-images.githubusercontent.com/97711667/164967698-5724439b-db17-430b-8f27-962186eda201.gif)

- 인기 페이지
![인기페이지](https://user-images.githubusercontent.com/97711667/164967349-7b3f4750-4bd6-4245-a182-45c199ba60bf.gif)

***
### AWS 배포 준비중...