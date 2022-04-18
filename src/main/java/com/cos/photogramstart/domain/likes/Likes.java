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
// 한 테이블에 여러개의 유니크 설정을 해주기 위한 어노테이션
// 1번유저는 1번 게시물에 좋아요를 여러번 할 수 없어야 한다. 때문에 두개를 유니크로 묶어준다.
@Table(
		uniqueConstraints = {
				@UniqueConstraint(
						name="likes_uk",
						columnNames = {"imageId", "userId"}
				)
		}
)
public class Likes { // N
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@JoinColumn(name = "imageId")
	@ManyToOne
	private Image image; // 하나의 이미지는 여러개의 좋아요를 받을 수 있다. 1:N

	// 무한참조 오류가 발생하였다. 왜?
	// image를 selcet하여 호출하면 image, user, likes의 정보를 받아온다.
	// 근데 likes안에 user의 정보가 담겨있고, 그 user 안에 image가 또 존재한다.
	// 그 image안에 likes의 정보가 또 담겨져 있고, 그 likes안에 또 user의 정보가 존재한다.
	// 그 user의 정보안에 또 image의 정보가 담겨있고, 그 image안에 또 likes가 존재한다.
	// 이러한 상황이 무한적으로 발생하면서 생기는 오류이다.
	// 처음에 호출했던 image안에 user의 정보는 문제가 없었다.
	// 근데 image안의 likes를 호출할때는 likes안의 image는 ignore처리가 되어있지만,
	// likes안의 user 정보가 담겨올 때 user안에서 다시 images가 튀어나올때가 문제가 된것이다.
	// 때문에 likes안에서 user의 정보를 담아올때 images를 ignore 해주어야 한다.
	
	@JsonIgnoreProperties({"images"})
	@JoinColumn(name = "userId")
	@ManyToOne
	private User user; // 한명의 유저는 여러개의 좋아요를 할 수 있다. 1:N
	
	private LocalDateTime createDate;
	
	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}


}
