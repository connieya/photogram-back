package com.cos.photogramstart.web.dto.image;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.web.dto.auth.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImagePopularDto {

    private int id;
    private String caption;
    private String postImageUrl;
    private long likeCount;
    private User user;
}
