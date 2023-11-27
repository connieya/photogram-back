package com.cos.photogramstart.web.api;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.config.baseresponse.ResponseEnum;
import com.cos.photogramstart.config.baseresponse.SuccessResponse;
import com.cos.photogramstart.service.PostService;
//import com.cos.photogramstart.service.S3Service;
import com.cos.photogramstart.service.S3Service;
import com.cos.photogramstart.web.dto.RespDto;
import com.cos.photogramstart.web.dto.post.PostData;
import com.cos.photogramstart.web.dto.post.PostPopularDto;
import com.cos.photogramstart.web.dto.post.PostUploadRequest;
import com.cos.photogramstart.web.dto.post.UserImageResponse;
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
    private final S3Service s3Service;

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

    @GetMapping("/image")
    public ResponseEntity<?> selectImages(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<PostData> images = postService.selectImages(principalDetails.getUser().getId());
        return new ResponseEntity<>(new RespDto<>(1, "성공", images), HttpStatus.OK);
    }



    @PostMapping(value = "/posts" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SuccessResponse<?> uploadPost(@ModelAttribute PostUploadRequest request ){
        postService.uploadPost(request);
        return new SuccessResponse<>(ResponseEnum.UPLOAD_SUCCESS);
    }


    @GetMapping("/image/popular")
    public ResponseEntity<?> popular(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<PostPopularDto> images = postService.popular();
        return new ResponseEntity<>(new RespDto<>(1, "인기 이미지 조회", images), HttpStatus.OK);
    }

}
