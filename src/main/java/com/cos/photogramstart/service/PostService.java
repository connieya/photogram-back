package com.cos.photogramstart.service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.post.Post;
import com.cos.photogramstart.domain.post.PostRepository;
import com.cos.photogramstart.web.dto.comment.CommentResponseDto;
import com.cos.photogramstart.web.dto.image.ImageData;
import com.cos.photogramstart.web.dto.image.ImagePopularDto;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import com.cos.photogramstart.web.dto.image.UserImageResponse;
import lombok.RequiredArgsConstructor;
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
public class PostService {

    private final PostRepository imageRepository;
    private final CommentService commentService;
    private final PostLikeService likesService;


    @Value("${base-url}")
    private String baseUrl;

    @Transactional(readOnly = true)
    public List<ImagePopularDto> popular() {
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
    public List<ImageData> selectImages(int principalId) {
        List<Post> images = imageRepository.getStory(principalId);
        List<ImageData> result = images.stream()
                .map(i -> new ImageData(i))
                .collect(Collectors.toList());
        result.forEach(o -> {
            o.setLikeState(likesService.likeState(o.getImageId(), principalId));
            List<CommentResponseDto> commentDto = commentService.findByImageId(o.getImageId());
            o.setComments(commentDto);
        });
        return result;
    }

    @Transactional
    public void upload(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + imageUploadDto.getFile().getOriginalFilename();
        Post image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser() , baseUrl);
        imageRepository.save(image);
    }

    public List<UserImageResponse> selectUserImages(int userId) {
        return imageRepository.selectUserImage(userId);
    }
}
