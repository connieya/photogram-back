package com.cos.photogramstart.domain.post.service;

import com.cos.photogramstart.domain.post.repository.PostLIkeRepository;
import com.cos.photogramstart.domain.post.repository.PostRepository;
import com.cos.photogramstart.domain.user.entity.User;
import com.cos.photogramstart.global.error.ErrorCode;
import com.cos.photogramstart.global.error.exception.EntityAlreadyExistException;
import com.cos.photogramstart.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLIkeRepository postLIkeRepository;
    private final PostRepository postRepository;
    private final AuthUtil authUtil;

    @Transactional
    public void like(int imageId , long principalId){
        postLIkeRepository.mLike(imageId,principalId);
    }

    @Transactional
    public void unLike(int imageId , long principalId){
        postLIkeRepository.mUnLike(imageId,principalId);
    }


    public boolean likeState(int imageId , int userId){
        return false;
//        return postLIkeRepository.findByImageIdAndUserId(imageId,userId) != null;
    }

    public void like(Long postId) {
        postRepository.findById(postId);

        User loginUser = authUtil.getLoginUser();
        if (postLIkeRepository.existsByPostIdAndUserId(postId,loginUser.getId())){
            throw new EntityAlreadyExistException(ErrorCode.POST_LIKE_ALREADY_EXIST);
        }

//        new PostLike

    }
}
