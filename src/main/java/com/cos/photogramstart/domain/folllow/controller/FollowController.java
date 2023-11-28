package com.cos.photogramstart.domain.folllow.controller;

import com.cos.photogramstart.global.config.security.auth.PrincipalDetails;
import com.cos.photogramstart.domain.folllow.service.FollowService;
import com.cos.photogramstart.global.result.ResultCode;
import com.cos.photogramstart.global.result.ResultResponse;
import com.cos.photogramstart.web.dto.RespDto;
import com.cos.photogramstart.web.dto.follow.FollowDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Api(tags = "팔로우 API")
public class FollowController {

    private final FollowService followService;

    @ApiOperation(value = "팔로우")
    @PostMapping("/follow/{username}")
    public ResponseEntity<?> follow(@PathVariable String username) {
        followService.follow(username);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.FOLLOW_SUCCESS));
    }

    @ApiOperation(value = "언팔로우")
    @DeleteMapping("/follow/{username}")
    public ResponseEntity<?> unfollow(@PathVariable String  username) {
        followService.unfollow(username);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.UNFOLLOW_SUCCESS));
    }

    @GetMapping("/following/{pageUserId}")
    public ResponseEntity<?> followingList(@PathVariable int pageUserId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
//        List<FollowDto> subscribeDto = followService.followingList(principalDetails.getUser().getId(), pageUserId);
        return new ResponseEntity<>(new RespDto<>(1, "팔로잉 리스트 불러오기 성공", ""), HttpStatus.OK);
    }

    @GetMapping("/follower/{pageUserId}")
    public ResponseEntity<?> followerList(@PathVariable int pageUserId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
//        List<FollowDto> followerDto = followService.followerList(principalDetails.getUser().getId(), pageUserId);
        return new ResponseEntity<>(new RespDto<>(1, "팔로워 리스트 불러오기 성공", ""), HttpStatus.OK);
    }
}
