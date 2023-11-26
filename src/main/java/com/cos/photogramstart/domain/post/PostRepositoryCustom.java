package com.cos.photogramstart.domain.post;

import com.cos.photogramstart.web.dto.image.ImagePopularDto;
import com.cos.photogramstart.web.dto.image.UserImageResponse;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> getStory(int principalId);
//    List<ImageData> getStory(int principalId);
    List<ImagePopularDto> popular();

    List<UserImageResponse> selectUserImage(int userId);
}
