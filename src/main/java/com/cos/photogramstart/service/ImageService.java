package com.cos.photogramstart.service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.domain.likes.LikesRepository;
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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final CommentService commentService;
    private final LikesService likesService;

    @Transactional(readOnly = true)
    public List<ImagePopularDto> popular() {
        return imageRepository.popular();
    }


    @Transactional(readOnly = true)
    public Page<Image> select(int principalId, Pageable pageable) {
        Page<Image> images = imageRepository.mStory(principalId, pageable);
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
        List<Image> images = imageRepository.getStory(principalId);
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

    @Value("${file.path}")
    private String uploadFolder;

    @Transactional
    public void upload(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
        // uuid 란?
        // 네트워크 상에서 고유성이 보장되는 id를 만들기 위한 표준 규약
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + imageUploadDto.getFile().getOriginalFilename();
        Path imageFilePath = Paths.get(uploadFolder + imageFileName);

        try {
            Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
        imageRepository.save(image);
    }

    public List<UserImageResponse> selectUserImages(int userId) {
        return imageRepository.selectUserImage(userId);
    }
}
