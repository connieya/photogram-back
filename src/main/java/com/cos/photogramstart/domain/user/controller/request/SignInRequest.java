package com.cos.photogramstart.domain.user.controller.request;

import com.cos.photogramstart.domain.user.repository.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest {

    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public User toUser(BCryptPasswordEncoder bCryptPasswordEncoder){
        return User.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication(){
        return new UsernamePasswordAuthenticationToken(username,password);
    }

}
