package com.cos.photogramstart.web;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/user/{pageUserId}")
    public String profile(@PathVariable int pageUserId, Model model , @AuthenticationPrincipal PrincipalDetails details) {
        UserProfileDto dto = userService.selectUserProfile(pageUserId, details.getUser().getId());
        model.addAttribute("dto",dto);
        return "user/profile";
    }

    @GetMapping("/user/{id}/update")
    public String update(@PathVariable int id , @AuthenticationPrincipal PrincipalDetails details){
//        User user = details.getUser();
//        System.out.println("세션 정보 : "+user );
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        PrincipalDetails principal = (PrincipalDetails) auth.getPrincipal();
//        System.out.println("직접 찾은 세션 정보 = "+ principal.getUser());
        return "user/update";
    }
}
