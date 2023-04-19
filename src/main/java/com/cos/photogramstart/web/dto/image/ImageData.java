package com.cos.photogramstart.web.dto.image;

import com.cos.photogramstart.domain.image.Image;

import com.cos.photogramstart.web.dto.comment.CommentResponseDto;
import lombok.Data;
import java.util.List;


@Data
public class ImageData {

    private String profileImageUrl;
    private String caption;
    private int userId;
    private String postImageUrl;
    private String username;
    private int imageId;
    private boolean likeState;
    private int likeCount;
    private List<CommentResponseDto> comments;

    public ImageData(Image image){
        this.profileImageUrl = image.getUser().getProfileImageUrl();
        this.userId = image.getUser().getId();
        this.postImageUrl = image.getPostImageUrl();
        this.username = image.getUser().getUsername();
        this.imageId = image.getId();
//        this.comments = image.getComments();
        this.likeCount = image.getLikes().size();
        this.likeState = false;
        this.caption = image.getCaption();
    }
}
