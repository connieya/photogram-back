package com.cos.photogramstart.domain.post.service;

import com.cos.photogramstart.global.config.security.auth.AuthUtil;
import com.cos.photogramstart.domain.post.entity.Post;
import com.cos.photogramstart.domain.post.repository.PostRepository;
import com.cos.photogramstart.domain.user.entity.User;
import com.cos.photogramstart.domain.comment.service.CommentService;
import com.cos.photogramstart.domain.likes.service.PostLikeService;
import com.cos.photogramstart.service.S3Service;
import com.cos.photogramstart.web.dto.comment.CommentResponseDto;
import com.cos.photogramstart.web.dto.post.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final AuthUtil authUtil;
    private final PostRepository imageRepository;
    private final CommentService commentService;
    private final PostLikeService likesService;
    private final S3Service s3Service;


    @Value("${base-url}")
    private String baseUrl;

    @Transactional(readOnly = true)
    public List<PostPopularDto> popular() {
        return imageRepository.popular();
    }


    @Transactional(readOnly = true)
    public Page<Post> select(int principalId, Pageable pageable) {
        Page<Post> images = imageRepository.mStory(principalId, pageable);
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
        List<Post> images = imageRepository.getStory(principalId);
        List<PostData> result = images.stream()
                .map(i -> new PostData(i))
                .collect(Collectors.toList());
        result.forEach(o -> {
            o.setLikeState(likesService.likeState(o.getImageId(), principalId));
            List<CommentResponseDto> commentDto = commentService.findByImageId(o.getImageId());
            o.setComments(commentDto);
        });
        return result;
    }

    public List<UserImageResponse> selectUserImages(int userId) {
        return imageRepository.selectUserImage(userId);
    }

    public void uploadPost(PostUploadRequest request) {
        User loginUser = authUtil.getLoginUser();
        s3Service.uploadImage(request.getFile(), "images/" + loginUser.getUsername());
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + request.getFile().getOriginalFilename();
        Post post = Post.builder().
                caption(request.getCaption())
                .location(request.getLocation())
                .postImageUrl(imageFileName)
                .user(loginUser)
                .baseUrl(baseUrl).build();
        imageRepository.save(post);

    }
}
