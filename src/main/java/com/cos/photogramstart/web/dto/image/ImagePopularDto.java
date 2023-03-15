package com.cos.photogramstart.web.dto.image;

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
    private String username;
    private int userId;
    private long likeCount;
}
