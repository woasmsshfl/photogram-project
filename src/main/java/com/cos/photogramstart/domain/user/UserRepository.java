package com.cos.photogramstart.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

// 어노테이션이 없어도 IoC컨테이너에 자동으로 등록된다.
// JpaRepository 가 이미 어노테이션을 가지고 있기 때문에.
public interface UserRepository extends JpaRepository<User, Integer> {

}
