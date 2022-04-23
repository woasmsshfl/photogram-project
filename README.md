# PHOTOGRAM PROJECT
- instagram clone codding 하기

### 프론트엔드 기초 세팅 
 <a href="https://github.com/codingspecialist/EaszUp-Springboot-Photogram-Start"> GITHUB </a>
 - 해당 링크에서 git clone 하여 프론드엔드 기반 데이터를 받았습니다.

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

- JSP template Engine

```xml
<dependency>
 <groupId>org.apache.tomcat</groupId>
 <artifactId>tomcat-jasper</artifactId>
 <version>9.0.43</version>
</dependency>
```

- JSTL(JSP 태그 라이브러리

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

-AOP

```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-aop</artifactId>
      <version>2.4.5</version>
  </dependency>
```

### OAuth2 개발자 센터

<a href="https://developers.facebook.com/" title="페이스북 개발자 센터로 이동" target="_blank">페이스북 개발자 센터</a>
<a href="https://console.cloud.google.com/getting-started?hl=ko" title="구글 개발자 센터로 이동" target="_blank">구글 개발자 센터</a>
<a href="https://developers.kakao.com/" title="카카오 개발자 센터로 이동" target="_blank">카카오 개발자 센터</a>
<a href="https://developers.naver.com/main/" title="네이버 개발자 센터로 이동" target="_blank">네이버 개발자 센터</a>

### 데이터베이스

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

### 태그라이브러리

```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
```
