package com.cos.photogramstart.domain.image;

import com.cos.photogramstart.web.dto.image.ImageData;
import com.cos.photogramstart.web.dto.image.ImagePopularDto;
import com.cos.photogramstart.web.dto.image.UserImageResponse;

import java.util.List;

public interface ImageRepositoryCustom {
    List<Image> getStory(int principalId);
//    List<ImageData> getStory(int principalId);
    List<ImagePopularDto> popular();

    List<UserImageResponse> selectUserImage(int userId);
}
