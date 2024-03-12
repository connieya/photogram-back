package com.cos.photogramstart.domain.folllow.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowResult {
    private int id;
    private String username;
    private String profileImageUrl;
    private boolean followState;
    private boolean isCurrentUser;
}
