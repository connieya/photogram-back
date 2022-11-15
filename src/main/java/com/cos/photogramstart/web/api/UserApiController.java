package com.cos.photogramstart.web.api;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.RespDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @PutMapping("/api/user/{id}")
    public RespDto<?> update(@PathVariable int id , UserUpdateDto userUpdateDto , @AuthenticationPrincipal PrincipalDetails principalDetails){
        User userEntity = userService.update(id, userUpdateDto.toEntity());
        principalDetails.setUser(userEntity);
        System.out.println("userUpdateDto "+userUpdateDto);
        return new RespDto<>(1,"회원수정완료",userUpdateDto.toEntity());
    }
}
