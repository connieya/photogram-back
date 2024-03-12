package com.cos.photogramstart.domain.post.infrastructure;

import com.cos.photogramstart.domain.post.domain.Post;
import com.cos.photogramstart.domain.post.presentation.PostPopularDto;
import com.cos.photogramstart.domain.post.application.UserImageResponse;

import java.util.List;

public interface PostCustomRepository {
    List<Post> getStory(int principalId);
//    List<ImageData> getStory(int principalId);
    List<PostPopularDto> popular();

    List<UserImageResponse> selectUserImage(int userId);
}
