package com.cos.photogramstart.web.api;

import com.cos.photogramstart.global.config.security.auth.PrincipalDetails;
import com.cos.photogramstart.domain.likes.service.PostLikeService;
import com.cos.photogramstart.web.dto.RespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostLikeController {

    private final PostLikeService likesService;

    @PostMapping("/likes/{imageId}")
    public ResponseEntity<?> like(@PathVariable int imageId , @AuthenticationPrincipal PrincipalDetails principalDetails){
        likesService.like(imageId , principalDetails.getUser().getId());
        return new ResponseEntity<>(new RespDto<>(1,"좋아요 성공",null), HttpStatus.CREATED);
    }

    @DeleteMapping("/likes/{imageId}")
    public ResponseEntity<?> unLike(@PathVariable int imageId , @AuthenticationPrincipal PrincipalDetails principalDetails){
        likesService.unLike(imageId,principalDetails.getUser().getId());
        return new ResponseEntity<>(new RespDto<>(1,"좋아요 취소 성공",null),HttpStatus.OK);
    }
}
