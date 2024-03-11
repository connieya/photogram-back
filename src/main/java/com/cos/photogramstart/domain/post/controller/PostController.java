package com.cos.photogramstart.domain.post.controller;

import com.cos.photogramstart.domain.post.service.PostLikeService;
import com.cos.photogramstart.domain.post.service.PostService;
import com.cos.photogramstart.global.aws.S3Uploader;
import com.cos.photogramstart.global.result.ResultResponse;
import com.cos.photogramstart.web.dto.post.PostPopularDto;
import com.cos.photogramstart.web.dto.post.PostUploadRequest;
import com.cos.photogramstart.web.dto.post.UserImageResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cos.photogramstart.global.result.ResultCode.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
@Slf4j
public class PostController {

    private final PostService postService;
    private final PostLikeService postLikeService;
    private final S3Uploader s3Service;



    @GetMapping("/user/images")
    public List<UserImageResponse> selectUserImages(@RequestParam int userId) {
        return postService.selectUserImages(userId);
    }


    @ApiOperation(value = "게시물 업로드" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping
    public ResponseEntity<ResultResponse> uploadPost(@ModelAttribute PostUploadRequest request ){
        postService.uploadPost(request);
        return ResponseEntity.ok(ResultResponse.of(CREATE_POST_SUCCESS));
    }

    @ApiOperation(value = "게시물 좋아요")
    @PostMapping("/like")
    public ResponseEntity<ResultResponse> likePost(@RequestParam Long postId){
        postLikeService.like(postId);
        return ResponseEntity.ok(ResultResponse.of(LIKE_POST_SUCCESS));

    }


    @GetMapping("/image/popular")
    public ResponseEntity<?> popular() {
        List<PostPopularDto> images = postService.popular();
        return ResponseEntity.ok(ResultResponse.of(POPULAR_FEED_GET_SUCCESS));
    }

}
