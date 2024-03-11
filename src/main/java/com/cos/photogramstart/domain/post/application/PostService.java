package com.cos.photogramstart.domain.post.application;

import com.cos.photogramstart.domain.post.presentation.PostPopularDto;
import com.cos.photogramstart.domain.post.presentation.PostUploadRequest;
import com.cos.photogramstart.domain.post.domain.PostImage;
import com.cos.photogramstart.domain.post.infrastructure.PostImageRepository;
import com.cos.photogramstart.domain.user.domain.Image;
import com.cos.photogramstart.global.util.AuthUtil;
import com.cos.photogramstart.domain.post.domain.Post;
import com.cos.photogramstart.domain.post.infrastructure.PostRepository;
import com.cos.photogramstart.domain.user.domain.User;
import com.cos.photogramstart.domain.comment.application.CommentService;
import com.cos.photogramstart.global.aws.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final AuthUtil authUtil;
    private final PostRepository postRepository;
    private final CommentService commentService;
    private final PostLikeService likesService;
    private final PostImageRepository postImageRepository;
    private final S3Uploader s3Uploader;


    @Value("${base-url}")
    private String baseUrl;

    @Transactional(readOnly = true)
    public List<PostPopularDto> popular() {
        return postRepository.popular();
    }


    @Transactional(readOnly = true)
    public Page<Post> select(int principalId, Pageable pageable) {
        Page<Post> images = postRepository.mStory(principalId, pageable);
//        images.forEach(image ->{
//            image.setLikeCount(image.getLikes().size());
//            image.getLikes().forEach(like->{
//                if (like.getUser().getId() == principalId){
//                 image.setLikeState(true);
//                }
//            });
//        });
        return images;
    }

    @Transactional(readOnly = true)
    public List<PostData> selectImages(int principalId) {
        List<Post> images = postRepository.getStory(principalId);
        List<PostData> result = images.stream()
                .map(i -> new PostData(i))
                .collect(Collectors.toList());
//        result.forEach(o -> {
//            o.setLikeState(likesService.likeState(o.getImageId(), principalId));
//            List<CommentResponseDto> commentDto = commentService.findByImageId(o.getImageId());
//            o.setComments(commentDto);
//        });
        return result;
    }

    public List<UserImageResponse> selectUserImages(int userId) {
        return postRepository.selectUserImage(userId);
    }

    @Transactional
    public void uploadPost(PostUploadRequest request) {
        User loginUser = authUtil.getLoginUser();
        Image image = s3Uploader.uploadImage(request.getFile(), "post");
        PostImage postImage = new PostImage(image);
        postImageRepository.save(postImage);
        Post post = Post.builder().
                caption(request.getCaption())
                .location(request.getLocation())
                .user(loginUser)
                .postImage(postImage).
                build();
        postRepository.save(post);

    }
}
