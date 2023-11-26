package com.cos.photogramstart.web.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostPopularDto {

    private int id;
    private String caption;
    private String postImageUrl;
    private long likeCount;
    private int userId;
    private String username;
    private String profileImageUrl;
}
