package com.cos.photogramstart.domain.post.controller;

import com.cos.photogramstart.global.config.security.auth.PrincipalDetails;
import com.cos.photogramstart.global.response.ResponseEnum;
import com.cos.photogramstart.global.response.SuccessResponse;
import com.cos.photogramstart.domain.post.service.PostService;
//import com.cos.photogramstart.global.aws.S3Service;
import com.cos.photogramstart.global.aws.S3Uploader;
import com.cos.photogramstart.global.result.ResultCode;
import com.cos.photogramstart.global.result.ResultResponse;
import com.cos.photogramstart.web.dto.RespDto;
import com.cos.photogramstart.web.dto.post.PostData;
import com.cos.photogramstart.web.dto.post.PostPopularDto;
import com.cos.photogramstart.web.dto.post.PostUploadRequest;
import com.cos.photogramstart.web.dto.post.UserImageResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Slf4j
public class PostController {

    private final PostService postService;
    private final S3Uploader s3Service;

//    @GetMapping("/api/image")
//    public ResponseEntity<?> select(@AuthenticationPrincipal PrincipalDetails principalDetails ,@PageableDefault(size = 3) Pageable pageable){
//
//        Page<Image> images = imageService.select(principalDetails.getUser().getId() ,pageable);
//        return new ResponseEntity<>(new RespDto<>(1,"성공",images), HttpStatus.OK);
//    }

    @GetMapping("/user/images")
    public List<UserImageResponse> selectUserImages(@RequestParam int userId) {
        return postService.selectUserImages(userId);
    }

//    @GetMapping("/image")
//    public ResponseEntity<?> selectImages(@AuthenticationPrincipal PrincipalDetails principalDetails) {
//        List<PostData> images = postService.selectImages(principalDetails.getUser().getId());
//        return new ResponseEntity<>(new RespDto<>(1, "성공", images), HttpStatus.OK);
//    }




    @ApiOperation(value = "게시물 업로드" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping(value = "/posts")
    public ResponseEntity<ResultResponse> uploadPost(@ModelAttribute PostUploadRequest request ){
        postService.uploadPost(request);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.CREATE_POST_SUCCESS));
    }


    @GetMapping("/image/popular")
    public ResponseEntity<?> popular(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<PostPopularDto> images = postService.popular();
        return new ResponseEntity<>(new RespDto<>(1, "인기 이미지 조회", images), HttpStatus.OK);
    }

}
