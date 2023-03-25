package com.cos.photogramstart.web.api;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.service.FollowService;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.RespDto;
import com.cos.photogramstart.web.dto.follow.FollowDto;
import com.cos.photogramstart.web.dto.user.UserProfileDto;
import com.cos.photogramstart.web.dto.user.UserProfileUpdateResponse;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final FollowService followService;

    @GetMapping("/api/user/{pageUserId}")
    public ResponseEntity<?> profile(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int pageUserId) {
        UserProfileDto dto = userService.selectUserProfile(pageUserId, principalDetails.getUser().getId());
        return new ResponseEntity<>(new RespDto<>(1,"유저 프로필 조회",dto),HttpStatus.OK);
    }

    @GetMapping("/api/users")
    public ResponseEntity<?> userList() {
        List<User> users = userService.selectUsers();
        return new ResponseEntity<>(new RespDto<>(1,"포토그램 회원 리스트",users),HttpStatus.OK);
    }

    @GetMapping("/api/user/profile")
    public ResponseEntity<?> update(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        if(principalDetails.getUser() == null){
            throw new CustomApiException("로그인이 필요합니다");
        }
        User user = principalDetails.getUser();
        UserProfileUpdateResponse profileUpdateResponse = UserProfileUpdateResponse.builder()
                .bio(user.getBio())
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .profileImageUrl(user.getProfileImageUrl())
                .website(user.getWebsite()).build();


        return new ResponseEntity<>(new RespDto<>(1,"유저 프로필 조회",profileUpdateResponse),HttpStatus.OK);
    }

    @PutMapping("/api/user/image")
    public ResponseEntity<?> profileImageUpdate(@RequestParam("file") MultipartFile profileImageFile , @AuthenticationPrincipal PrincipalDetails principalDetails){
        System.out.println("profileImageFile = " + profileImageFile);
        User userEntity = userService.updateImage(principalDetails.getUser().getId(),profileImageFile);
        principalDetails.setUser(userEntity);
        return new ResponseEntity<>(new RespDto<>(1,"프로필 사진 변경",null),HttpStatus.OK);
    }



    @GetMapping("/api/following/{pageUserId}")
    public ResponseEntity<?> followingList(@PathVariable int pageUserId , @AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<FollowDto> subscribeDto = followService.followingList(principalDetails.getUser().getId(),pageUserId);
        return new ResponseEntity<>(new RespDto<>(1,"팔로잉 리스트 불러오기 성공",subscribeDto), HttpStatus.OK);
    }

    @GetMapping("/api/follower/{pageUserId}")
    public ResponseEntity<?> followerList(@PathVariable int pageUserId , @AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<FollowDto> followerDto = followService.followerList(principalDetails.getUser().getId(),pageUserId);
        System.out.println("followerDto = " + followerDto);
        return new ResponseEntity<>(new RespDto<>(1,"팔로워 리스트 불러오기 성공",followerDto), HttpStatus.OK);
    }

    @PutMapping("/api/user")
    public RespDto<?> updateProfile(
                             @Valid @RequestBody UserUpdateDto userUpdateDto ,
                             BindingResult bindingResult,
                             @AuthenticationPrincipal PrincipalDetails principalDetails){
        if (bindingResult.hasErrors()){
            Map<String,String> errorMap = new HashMap<>();
            for(FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(),error.getDefaultMessage());
            }
            throw new CustomApiException("유효성 검사 실패함");
        }

        if (principalDetails.getUser() == null){
            throw new CustomApiException("로그인이 필요합니다");
        }

        User userEntity = userService.update(principalDetails.getUser().getId(), userUpdateDto.toEntity());
        principalDetails.setUser(userEntity);
        return new RespDto<>(1,"유저 정보 수정 ",userEntity);
    }
}
