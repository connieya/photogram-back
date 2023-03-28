package com.cos.photogramstart.domain.image;

import com.cos.photogramstart.web.dto.image.ImageData;
import com.cos.photogramstart.web.dto.image.ImagePopularDto;

import java.util.List;

public interface ImageRepositoryCustom {
    List<ImageData> getStory(int principalId);
    List<ImagePopularDto> popular();
}
