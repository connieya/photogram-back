package com.cos.photogramstart.domain.user.repository.result;

import com.cos.photogramstart.domain.user.repository.User;
import com.cos.photogramstart.global.common.Image;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel("유저 프로필 조회 응답 모델")
@Getter
@Builder
public class UserProfileResponse {

    @ApiModelProperty(value = "사용자 이름" , example = "cony")
    private String username;
    @ApiModelProperty(value = "이름" , example = "박건희")
    private String name;
    @ApiModelProperty(value = "웹 사이트", example = "https://github.com/connieya")
    private String website;

    @ApiModelProperty(value = "프로필 사진")
    private Image profileImage;

    @ApiModelProperty(value = "본인 여부" ,example = "false")
    private boolean pageOwner;

    @QueryProjection
    @Builder
    public UserProfileResponse(String username, String name, String website, Image profileImage, boolean pageOwner) {
        this.username = username;
        this.name = name;
        this.website = website;
        this.profileImage = profileImage;
        this.pageOwner = pageOwner;
    }

}
