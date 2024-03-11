package com.cos.photogramstart.domain.user.controller;

import com.cos.photogramstart.global.config.security.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.repository.User;
import com.cos.photogramstart.global.result.ResultResponse;
import com.cos.photogramstart.domain.user.service.UserService;
import com.cos.photogramstart.domain.user.controller.response.UserInfo;
import com.cos.photogramstart.domain.user.repository.result.UserProfileResponse;
import com.cos.photogramstart.domain.user.controller.request.UserUpdateRequest;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

import static com.cos.photogramstart.global.result.ResultCode.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "유저 프로필 조회")
    @GetMapping("/{username}")
    public ResponseEntity<ResultResponse> getUserProfile(@PathVariable("username") String username) {
        UserProfileResponse response = userService.getUserProfile(username);
        return ResponseEntity.ok(ResultResponse.of(USER_GET_SUCCESS, response));
    }

    @ApiOperation(value = "회원 프로필 사진 업로드")
    @PostMapping("/image")
    public ResponseEntity<ResultResponse> uploadProfileImage(@RequestParam("file") MultipartFile uploadedImage) {
        userService.uploadProfileImage(uploadedImage);
        return ResponseEntity.ok(ResultResponse.of(REGISTER_SUCCESS));
    }

    @ApiOperation(value = "회원 프로필 삭제")
    @DeleteMapping("/image")
    public ResponseEntity<ResultResponse> deleteProfileImage() {
        userService.deleteProfileImage();
        return ResponseEntity.ok(ResultResponse.of(LOGIN_SUCCESS));
    }

    @GetMapping("/users")
    public ResponseEntity<?> userList() {
        List<UserInfo> users = userService.selectUsers();
        return ResponseEntity.ok(ResultResponse.of(USER_LIST_GET_SUCCESS, users));
    }

    @PutMapping("/user")
    public ResponseEntity<?> updateProfile(
            @Valid @RequestBody UserUpdateRequest userUpdateDto) {
        userService.update(userUpdateDto.toCommand());
        return ResponseEntity.ok(ResultResponse.of(USER_PROFILE_PUT_SUCCESS));
    }
}
