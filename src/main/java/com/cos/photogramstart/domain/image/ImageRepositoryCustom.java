package com.cos.photogramstart.domain.image;

import java.util.List;

public interface ImageRepositoryCustom {
    List<Image> getStory(int principalId);
}
