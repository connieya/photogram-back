package com.cos.photogramstart.web.api;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.service.LikesService;
import com.cos.photogramstart.web.dto.RespDto;
import com.cos.photogramstart.web.dto.image.ImageData;
import com.cos.photogramstart.web.dto.image.ImagePopularDto;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import com.cos.photogramstart.web.dto.image.UserImageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class ImageApiController {

    private final ImageService imageService;
    private final LikesService likesService;

//    @GetMapping("/api/image")
//    public ResponseEntity<?> select(@AuthenticationPrincipal PrincipalDetails principalDetails ,@PageableDefault(size = 3) Pageable pageable){
//        if (principalDetails == null){
//            return new ResponseEntity<>(new RespDto<>(-1,"로그인이 필요합니다.",null), HttpStatus.OK);
//        }
//        Page<Image> images = imageService.select(principalDetails.getUser().getId() ,pageable);
//        return new ResponseEntity<>(new RespDto<>(1,"성공",images), HttpStatus.OK);
//    }

    @GetMapping("/api/user/image")
    public List<UserImageResponse> selectUserImages(@RequestParam int userId) {
        return imageService.selectUserImages(userId);
    }

    @GetMapping("/api/image")
    public ResponseEntity<?> selectImages(@AuthenticationPrincipal PrincipalDetails principalDetails){
        List<ImageData> images = imageService.selectImages(principalDetails.getUser().getId());
        return new ResponseEntity<>(new RespDto<>(1,"성공",images), HttpStatus.OK);
    }

    @PostMapping("/api/likes/{imageId}")
    public ResponseEntity<?> like(@PathVariable int imageId , @AuthenticationPrincipal PrincipalDetails principalDetails){
        likesService.like(imageId , principalDetails.getUser().getId());
        return new ResponseEntity<>(new RespDto<>(1,"좋아요 성공",null),HttpStatus.CREATED);
    }

    @DeleteMapping("/api/likes/{imageId}")
    public ResponseEntity<?> unLike(@PathVariable int imageId , @AuthenticationPrincipal PrincipalDetails principalDetails){
        likesService.unLike(imageId,principalDetails.getUser().getId());
        return new ResponseEntity<>(new RespDto<>(1,"좋아요 취소 성공",null),HttpStatus.OK);
    }

    @PostMapping( "/api/image")
    public ResponseEntity<?>  imageUpload(@RequestParam("file") MultipartFile file, @RequestParam("caption") String caption, @AuthenticationPrincipal PrincipalDetails principalDetails){
        if (file.isEmpty()) {
            throw new CustomApiException("이미지가 첨부되지 않았습니다.");
        }
        ImageUploadDto imageUploadDto = new ImageUploadDto(file, caption);
        imageService.upload(imageUploadDto, principalDetails);
        return new ResponseEntity<>(new RespDto<>(1,"이미지 업로드  성공",principalDetails.getUser().getId()),HttpStatus.OK);
    }

    @GetMapping("/api/image/popular")
    public ResponseEntity<?> popular(@AuthenticationPrincipal PrincipalDetails principalDetails){
        if (principalDetails == null){
            return new ResponseEntity<>(new RespDto<>(-1,"로그인이 필요합니다.",null), HttpStatus.OK);
        }
        List<ImagePopularDto> images = imageService.popular();
        return new ResponseEntity<>(new RespDto<>(1,"인기 이미지 조회",images), HttpStatus.OK);
    }

}
