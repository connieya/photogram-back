package com.cos.photogramstart.domain.user.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserInfo {

    private Long id;
    private String username;
}
