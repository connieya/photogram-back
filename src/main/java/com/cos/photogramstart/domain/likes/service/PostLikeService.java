package com.cos.photogramstart.domain.likes.service;

import com.cos.photogramstart.domain.likes.repository.PostLIkeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLIkeRepository postLIkeRepository;

    @Transactional
    public void like(int imageId , int principalId){
        postLIkeRepository.mLike(imageId,principalId);
    }

    @Transactional
    public void unLike(int imageId , int principalId){
        postLIkeRepository.mUnLike(imageId,principalId);
    }


    public boolean likeState(int imageId , int userId){
        return false;
//        return postLIkeRepository.findByImageIdAndUserId(imageId,userId) != null;
    }
}
