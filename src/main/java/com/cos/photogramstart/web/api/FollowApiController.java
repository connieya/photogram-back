package com.cos.photogramstart.web.api;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.service.FollowService;
import com.cos.photogramstart.web.dto.RespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FollowApiController {

    private final FollowService followService;

    @PostMapping("/api/subscribe/{toUserId}")
    public ResponseEntity<?> follow(@AuthenticationPrincipal PrincipalDetails principalDetails , @PathVariable int toUserId){
        followService.follow(principalDetails.getUser().getId() , toUserId);
        return new ResponseEntity<>(new RespDto<>(1, "팔로우 성공",null), HttpStatus.OK);
    }

    @DeleteMapping("/api/subscribe/{toUserId}")
    public ResponseEntity<?> unfollow(@AuthenticationPrincipal PrincipalDetails principalDetails , @PathVariable int toUserId) {
        followService.unfollow(principalDetails.getUser().getId() , toUserId);
        return new ResponseEntity<>(new RespDto<>(1, "팔로우 취소 성공",null), HttpStatus.OK);
    }
}
