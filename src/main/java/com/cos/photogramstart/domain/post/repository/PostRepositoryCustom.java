package com.cos.photogramstart.domain.post.repository;

import com.cos.photogramstart.domain.post.entity.Post;
import com.cos.photogramstart.web.dto.post.PostPopularDto;
import com.cos.photogramstart.web.dto.post.UserImageResponse;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> getStory(int principalId);
//    List<ImageData> getStory(int principalId);
    List<PostPopularDto> popular();

    List<UserImageResponse> selectUserImage(int userId);
}
