package com.cos.photogramstart.web.dto.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClaimDto {

    private int id;
    private String nickname;
    private String email;
}
