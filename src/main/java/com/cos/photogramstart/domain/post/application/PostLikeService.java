package com.cos.photogramstart.domain.post.application;

import com.cos.photogramstart.domain.post.domain.Post;
import com.cos.photogramstart.domain.post.domain.PostLike;
import com.cos.photogramstart.domain.post.infrastructure.PostLikeRepository;
import com.cos.photogramstart.domain.post.infrastructure.PostRepository;
import com.cos.photogramstart.domain.user.domain.User;
import com.cos.photogramstart.global.error.exception.EntityAlreadyExistException;
import com.cos.photogramstart.global.error.exception.EntityNotFoundException;
import com.cos.photogramstart.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cos.photogramstart.global.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final AuthUtil authUtil;


    @Transactional
    public void unLike(Long postId){
        postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(POST_NOT_FOUND));
        User loginUser = authUtil.getLoginUser();
        PostLike postLike = postLikeRepository.findByPostIdAndUserId(postId, loginUser.getId());
        postLikeRepository.delete(postLike);
    }


    @Transactional
    public void like(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(POST_NOT_FOUND));

        User loginUser = authUtil.getLoginUser();
        if (postLikeRepository.existsByPostIdAndUserId(postId,loginUser.getId())){
            throw new EntityAlreadyExistException(POST_LIKE_ALREADY_EXIST);
        }

        PostLike postLike = new PostLike(post, loginUser);
        postLikeRepository.save(postLike);

    }
}
