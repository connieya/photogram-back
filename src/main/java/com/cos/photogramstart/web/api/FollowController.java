package com.cos.photogramstart.web.api;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.folllow.service.FollowService;
import com.cos.photogramstart.web.dto.RespDto;
import com.cos.photogramstart.web.dto.follow.FollowDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/follow/{toUserId}")
    public ResponseEntity<?> follow(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int toUserId) {
        followService.follow(principalDetails.getUser().getId(), toUserId);
        return new ResponseEntity<>(new RespDto<>(1, "팔로우 성공", null), HttpStatus.OK);
    }

    @DeleteMapping("/follow/{toUserId}")
    public ResponseEntity<?> unfollow(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int toUserId) {
        followService.unfollow(principalDetails.getUser().getId(), toUserId);
        return new ResponseEntity<>(new RespDto<>(1, "팔로우 취소 성공", null), HttpStatus.OK);
    }

    @GetMapping("/following/{pageUserId}")
    public ResponseEntity<?> followingList(@PathVariable int pageUserId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<FollowDto> subscribeDto = followService.followingList(principalDetails.getUser().getId(), pageUserId);
        return new ResponseEntity<>(new RespDto<>(1, "팔로잉 리스트 불러오기 성공", subscribeDto), HttpStatus.OK);
    }

    @GetMapping("/follower/{pageUserId}")
    public ResponseEntity<?> followerList(@PathVariable int pageUserId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<FollowDto> followerDto = followService.followerList(principalDetails.getUser().getId(), pageUserId);
        return new ResponseEntity<>(new RespDto<>(1, "팔로워 리스트 불러오기 성공", followerDto), HttpStatus.OK);
    }
}
