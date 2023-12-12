package com.cos.photogramstart.domain.user.controller;

import com.cos.photogramstart.global.config.security.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.entity.User;
import com.cos.photogramstart.global.result.ResultCode;
import com.cos.photogramstart.global.result.ResultResponse;
import com.cos.photogramstart.handler.exception.CustomApiException;
import com.cos.photogramstart.domain.user.service.UserService;
import com.cos.photogramstart.web.dto.RespDto;
import com.cos.photogramstart.web.dto.auth.UserInfo;
import com.cos.photogramstart.domain.user.dto.UserProfileResponse;
import com.cos.photogramstart.web.dto.user.UserProfileUpdateResponse;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "유저 프로필 조회")
    @GetMapping("/{username}")
    public ResponseEntity<ResultResponse> getUserProfile(@PathVariable("username") String username) {
        UserProfileResponse response = userService.getUserProfile(username);
        return ResponseEntity.ok(ResultResponse.of( ResultCode.USER_GET_SUCCESS,response));
    }

    @ApiOperation(value = "회원 프로필 사진 업로드")
    @PostMapping("/image")
    public ResponseEntity<ResultResponse> uploadProfileImage(@RequestParam("file") MultipartFile uploadedImage) {
        userService.uploadProfileImage(uploadedImage);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.REGISTER_SUCCESS));
    }

    @ApiOperation(value = "회원 프로필 삭제")
    @DeleteMapping("/image")
    public ResponseEntity<ResultResponse> deleteProfileImage() {
        userService.deleteProfileImage();
        return ResponseEntity.ok(ResultResponse.of(ResultCode.LOGIN_SUCCESS));
    }


    @GetMapping("/users")
    public ResponseEntity<?> userList() {
        List<UserInfo> users = userService.selectUsers();
        return new ResponseEntity<>(new RespDto<>(1, "포토그램 회원 리스트", users), HttpStatus.OK);
    }

    @GetMapping("/user/profile")
    public ResponseEntity<?> update(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (principalDetails.getUser() == null) {
            throw new CustomApiException("로그인이 필요합니다");
        }
        User user = principalDetails.getUser();
        UserProfileUpdateResponse profileUpdateResponse = UserProfileUpdateResponse.builder()
                .bio(user.getBio())
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .website(user.getWebsite()).build();


        return new ResponseEntity<>(new RespDto<>(1, "유저 프로필 조회", profileUpdateResponse), HttpStatus.OK);
    }



    @PutMapping("/user")
    public RespDto<?> updateProfile(
            @Valid @RequestBody UserUpdateDto userUpdateDto,
            BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (principalDetails.getUser() == null) {
            throw new CustomApiException("로그인이 필요합니다");
        }

        User userEntity = userService.update(principalDetails.getUser().getId(), userUpdateDto.toEntity());
        principalDetails.setUser(userEntity);
        return new RespDto<>(1, "유저 정보 수정 ", userEntity);
    }
}
