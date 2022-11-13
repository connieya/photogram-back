package com.cos.photogramstart.web;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class AuthController {

    private final AuthService authService;


    @GetMapping("/auth/signin")
    public String signinForm(){
        return "auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signupForm(){
        return "auth/signup";
    }

    @PostMapping("/auth/signup")
    public @ResponseBody String signup(@Valid SignupDto dto, BindingResult bindingResult){ // key=value(x-www-form-urlencoded)
        if (bindingResult.hasErrors()){
            Map<String,String> errorMap = new HashMap<>();
            for(FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(),error.getDefaultMessage());
                System.out.println(error.getDefaultMessage());
            }
            return "오류남";
        }
        User user = dto.toEntity();
        User userEntity = authService.signup(user);
        System.out.println("userEntity = " + userEntity);
        return "auth/signin";
    }
}
