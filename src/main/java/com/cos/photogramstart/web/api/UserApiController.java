package com.cos.photogramstart.web.api;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.SubscribeService;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.RespDto;
import com.cos.photogramstart.web.dto.auth.UserInfo;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;
import com.cos.photogramstart.web.dto.user.UserProfileDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final SubscribeService subscribeService;

    @GetMapping("/api/user/{pageUserId}")
    public ResponseEntity<?> profile(@PathVariable int pageUserId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        UserProfileDto dto = userService.getUserProfile(pageUserId, username);
        return new ResponseEntity<>(new RespDto<>(1,"유저 프로필 조회",dto),HttpStatus.OK);
    }

    @PutMapping("/api/user/{principalId}/profileImageUrl")
    public ResponseEntity<?> profileImageUrlUpdate(@PathVariable int principalId , MultipartFile profileImageFile ,@AuthenticationPrincipal PrincipalDetails principalDetails){
        User userEntity = userService.updateImage(principalId,profileImageFile);
        principalDetails.setUser(userEntity);
        return new ResponseEntity<>(new RespDto<>(1,"프로필 사진 변경",null),HttpStatus.OK);
    }

    @GetMapping("/api/user/{pageUserId}/subscribe")
    public ResponseEntity<?> subscribeList(@PathVariable int pageUserId , @AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<SubscribeDto> subscribeDto = subscribeService.selectSubscribe(principalDetails.getUser().getId(),pageUserId);

        return new ResponseEntity<>(new RespDto<>(1,"팔로잉 리스트 불러오기 성공",subscribeDto), HttpStatus.OK);
    }

    @GetMapping("/api/user/{pageUserId}/subscribed")
    public ResponseEntity<?> subscribedList(@PathVariable int pageUserId , @AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<SubscribeDto> subscribeDto = subscribeService.selectSubscribed(principalDetails.getUser().getId(),pageUserId);

        return new ResponseEntity<>(new RespDto<>(1,"팔로워 리스트 불러오기 성공",subscribeDto), HttpStatus.OK);
    }

    @PutMapping("/api/user/{id}")
    public RespDto<?> update(@PathVariable int id ,
                             @Valid UserUpdateDto userUpdateDto ,
                             BindingResult bindingResult,
                             @AuthenticationPrincipal PrincipalDetails principalDetails){
        if (bindingResult.hasErrors()){
            Map<String,String> errorMap = new HashMap<>();
            for(FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(),error.getDefaultMessage());
            }
            throw new CustomValidationApiException("유효성 검사 실패함", errorMap);
        }

        User userEntity = userService.update(id, userUpdateDto.toEntity());
        principalDetails.setUser(userEntity);
        return new RespDto<>(1,"회원수정완료",userEntity);
    }
}
