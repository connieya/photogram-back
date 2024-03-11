package com.cos.photogramstart.domain.folllow.controller;

import com.cos.photogramstart.domain.folllow.service.FollowService;
import com.cos.photogramstart.global.result.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.cos.photogramstart.global.result.ResultCode.*;

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
        return ResponseEntity.ok(ResultResponse.of(FOLLOW_SUCCESS));
    }

    @ApiOperation(value = "언팔로우")
    @DeleteMapping("/follow/{username}")
    public ResponseEntity<?> unfollow(@PathVariable String username) {
        followService.unfollow(username);
        return ResponseEntity.ok(ResultResponse.of(UNFOLLOW_SUCCESS));
    }

    @ApiOperation(value = "팔로잉 목록 조회")
    @GetMapping("/following/{username}")
    public ResponseEntity<?> followingList(@PathVariable String username) {
        followService.getFollowings(username);
        return ResponseEntity.ok(ResultResponse.of(GET_FOLLOWINGS_SUCCESS));
    }

    @ApiOperation(value = "팔로워 목록 조회")
    @GetMapping("/follower/{username}")
    public ResponseEntity<ResultResponse> followerList(@PathVariable String username) {
        return ResponseEntity.ok(ResultResponse.of(GET_FOLLOWERS_SUCCESS));
    }
}
