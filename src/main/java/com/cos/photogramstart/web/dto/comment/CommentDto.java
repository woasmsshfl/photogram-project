package com.cos.photogramstart.web.dto.comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CommentDto {

    @NotBlank // 빈값이거나 null 체크 그리고 빈 공백까지
    private String content;

    @NotNull // null 체크
    private Integer imageId;

    // toEntity가 필요없다.
}
