package com.cos.photogramstart.domain.post.application;

import com.cos.photogramstart.domain.post.domain.PostImage;
import com.cos.photogramstart.domain.post.exception.PostDeleteException;
import com.cos.photogramstart.domain.post.infrastructure.PostImageRepository;
import com.cos.photogramstart.domain.user.domain.Image;
import com.cos.photogramstart.global.error.ErrorCode;
import com.cos.photogramstart.global.error.exception.EntityNotFoundException;
import com.cos.photogramstart.global.util.AuthUtil;
import com.cos.photogramstart.domain.post.domain.Post;
import com.cos.photogramstart.domain.post.infrastructure.PostRepository;
import com.cos.photogramstart.domain.user.domain.User;
import com.cos.photogramstart.global.aws.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.cos.photogramstart.global.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final AuthUtil authUtil;
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final S3Uploader s3Uploader;


    @Transactional(readOnly = true)
    public List<PostResult> findAll() {
        User loginUser = authUtil.getLoginUser();
        return postRepository.findAll(loginUser.getId());
    }

    @Transactional(readOnly = true)
    public PostDetail findPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(POST_NOT_FOUND));
        return PostDetail.create(post);
    }



    @Transactional
    public void upload(PostUpload postUpload, MultipartFile file) {
        User loginUser = authUtil.getLoginUser();
        Image image = s3Uploader.uploadImage(file, "post");
        PostImage postImage = new PostImage(image);
        postImageRepository.save(postImage);
        postRepository.save(
                Post.create(postUpload, loginUser, postImage)
        );

    }

    @Transactional
    public void delete(Long postId) {
        User loginUser = authUtil.getLoginUser();
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(POST_NOT_FOUND));
        User user = findPost.getUser();
        if (!user.getId().equals(loginUser.getId())){
            throw new PostDeleteException(POST_CANT_DELETE);
        }
        findPost.deleteComment();
        findPost.deletePostLike();
        postRepository.delete(findPost);
    }
}
