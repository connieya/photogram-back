package com.cos.photogramstart.domain.post.application;

import com.cos.photogramstart.domain.post.domain.Post;

import com.cos.photogramstart.domain.comment.application.CommentResponseDto;
import lombok.Data;
import java.util.List;


@Data
public class PostData {

    private String profileImageUrl;
    private String caption;
    private Long userId;
    private String postImageUrl;
    private String username;
    private Long imageId;
    private boolean likeState;
    private int likeCount;
    private List<CommentResponseDto> comments;

    public PostData(Post image){
        this.userId = image.getUser().getId();
        this.username = image.getUser().getUsername();
        this.imageId = image.getId();
        this.likeCount = image.getPostLikes().size();
        this.caption = image.getCaption();
    }
}
