package com.cos.photogramstart.domain.user.application.result;

import com.cos.photogramstart.domain.user.domain.Image;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel("유저 프로필 조회 응답 모델")
@Getter
@NoArgsConstructor
public class UserProfileResult {

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

    private Long followerCount;
    private Long followingCount;

    public void setFollowerCount(Long followerCount) {
        this.followerCount = followerCount;
    }

    public void setFollowingCount(Long followingCount) {
        this.followingCount = followingCount;
    }

}
