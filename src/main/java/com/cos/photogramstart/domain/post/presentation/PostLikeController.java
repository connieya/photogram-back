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

    @PostMapping("/likes/{imageId}")
    public ResponseEntity<?> like(@PathVariable int imageId , @AuthenticationPrincipal PrincipalDetails principalDetails){
        likesService.like(imageId , principalDetails.getUser().getId());
        return ResponseEntity.ok(ResultResponse.of(LIKE_POST_SUCCESS));
    }

    @DeleteMapping("/likes/{imageId}")
    public ResponseEntity<?> unLike(@PathVariable int imageId , @AuthenticationPrincipal PrincipalDetails principalDetails){
        likesService.unLike(imageId,principalDetails.getUser().getId());
        return ResponseEntity.ok(ResultResponse.of(UN_LIKE_POST_SUCCESS));
    }
}
