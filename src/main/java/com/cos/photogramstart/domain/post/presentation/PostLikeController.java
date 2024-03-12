package com.cos.photogramstart.domain.post.presentation;

import com.cos.photogramstart.global.config.security.auth.PrincipalDetails;
import com.cos.photogramstart.domain.post.application.PostLikeService;
import com.cos.photogramstart.global.result.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.cos.photogramstart.global.result.ResultCode.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostLikeController {

    private final PostLikeService likesService;

    @PostMapping("/likes/{postId}")
    public ResponseEntity<?> like(@PathVariable Long postId ){
        likesService.like(postId);
        return ResponseEntity.ok(ResultResponse.of(LIKE_POST_SUCCESS));
    }

    @DeleteMapping("/likes/{postId}")
    public ResponseEntity<?> unLike(@PathVariable Long postId){
        likesService.unLike(postId);
        return ResponseEntity.ok(ResultResponse.of(UN_LIKE_POST_SUCCESS));
    }
}