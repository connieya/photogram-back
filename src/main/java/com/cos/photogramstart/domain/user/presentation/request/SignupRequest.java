package com.cos.photogramstart.domain.user.presentation.request;

import com.cos.photogramstart.domain.user.application.command.SignUpCommand;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter @Builder
public class SignupRequest {
    @Size(min = 2 , max = 10)
    @NotBlank(message = "유저 네임은 필수 입니다.")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "유저 네임은 영문으로만 입력 가능 합니다.")
    private String username;
    @Size(min = 4, max = 12)
    @NotBlank
    private String password;
    @NotBlank(message = "이메일은 필수 입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z.-]+\\.[a-zA-Z]{2,}$", message = "올바른 이메일 주소 형식이 아닙니다.")
    private String email;
    @NotBlank(message = "이름은 필수 입니다.")
    @Size(min = 2 , max = 10)
    private String name;

    public SignUpCommand toCommand() {
        return SignUpCommand
                .builder()
                .username(username)
                .password(password)
                .email(email)
                .name(name)
                .build();
    }

    public boolean validateName() {
        return name.matches("^[ㄱ-ㅎ가-힣\\s]*$") || name.matches("^[a-zA-Z\\s]*$");
    }


}