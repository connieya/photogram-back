package com.cos.photogramstart.domain.folllow.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowDto {
    private int id;
    private String username;
    private String profileImageUrl;
    private boolean followState;
    private boolean equalUserState;
}
