package com.cos.photogramstart.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer> {

	// 복잡한 쿼리는 직접 작성해서 선언해준다.
	// : 이 붙으면 받아온 데이터를 변수로써 삽입한다는 뜻이다.

	// 구독하기
	@Modifying // INSERT, DELETE, UPDATE 를 네이티브 쿼리로 작성하려면 해당 어노테이션 필요!!
	@Query(value = "INSERT INTO subscribe(fromUserId, toUserId, createDate) VALUES(:fromUserId, :toUserId, now())", nativeQuery = true)
	void mSubscribe(@Param("fromUserId") int fromUserId, @Param("toUserId") int toUserId);

	// 구독취소하기
	@Modifying
	@Query(value = "DELETE FROM subscribe WHERE fromUserId = :fromUserId AND toUserId = :toUserId", nativeQuery = true)
	void mUnSubscribe(@Param("fromUserId") int fromUserId, @Param("toUserId") int toUserId);

	// 구독 상태
	@Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId = :principalId AND toUserId = :pageUserId", nativeQuery = true)
	int mSubscribeState(@Param("principalId") int principalId, @Param("pageUserId") int pageUserId);

	// 구독자 수
	@Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId = :pageUserId", nativeQuery = true)
	int mSubscribeCount(@Param("pageUserId") int pageUserId);

}
