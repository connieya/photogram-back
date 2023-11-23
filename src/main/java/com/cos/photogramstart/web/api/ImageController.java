package com.cos.photogramstart.web.api;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.handler.exception.CustomApiException;
import com.cos.photogramstart.service.ImageService;
//import com.cos.photogramstart.service.S3Service;
import com.cos.photogramstart.service.S3Service;
import com.cos.photogramstart.web.dto.RespDto;
import com.cos.photogramstart.web.dto.image.ImageData;
import com.cos.photogramstart.web.dto.image.ImagePopularDto;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import com.cos.photogramstart.web.dto.image.UserImageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Slf4j
public class ImageController {

    private final ImageService imageService;
    private final S3Service s3Service;

//    @GetMapping("/api/image")
//    public ResponseEntity<?> select(@AuthenticationPrincipal PrincipalDetails principalDetails ,@PageableDefault(size = 3) Pageable pageable){
//
//        Page<Image> images = imageService.select(principalDetails.getUser().getId() ,pageable);
//        return new ResponseEntity<>(new RespDto<>(1,"성공",images), HttpStatus.OK);
//    }

    @GetMapping("/user/images")
    public List<UserImageResponse> selectUserImages(@RequestParam int userId) {
        return imageService.selectUserImages(userId);
    }

    @GetMapping("/image")
    public ResponseEntity<?> selectImages(@AuthenticationPrincipal PrincipalDetails principalDetails){
        List<ImageData> images = imageService.selectImages(principalDetails.getUser().getId());
        return new ResponseEntity<>(new RespDto<>(1,"성공",images), HttpStatus.OK);
    }


    @PostMapping( "/image")
    public ResponseEntity<?>  imageUpload(@RequestParam("file") MultipartFile file, @RequestParam("caption") String caption, @AuthenticationPrincipal PrincipalDetails principalDetails){
        if (file.isEmpty()) {
            throw new CustomApiException("이미지가 첨부되지 않았습니다.");
        }
        ImageUploadDto imageUploadDto = new ImageUploadDto(file, caption);
        try {
            log.info("파일 =  {}"  , file);
            s3Service.uploadImage(file ,"images");

        } catch (IOException e) {
            log.info("이미지 업로드 실패");
            throw new RuntimeException(e);
        }
        log.info("이미지 업로드 ={}" ,imageUploadDto);
//        s3Service.uploadImage(file,"");
//        imageService.upload(imageUploadDto, principalDetails);
        return null;
//        return new ResponseEntity<>(new RespDto<>(1,"이미지 업로드  성공",principalDetails.getUser().getId()),HttpStatus.OK);
    }



    @GetMapping("/image/popular")
    public ResponseEntity<?> popular(@AuthenticationPrincipal PrincipalDetails principalDetails){
        List<ImagePopularDto> images = imageService.popular();
        return new ResponseEntity<>(new RespDto<>(1,"인기 이미지 조회",images), HttpStatus.OK);
    }

}
