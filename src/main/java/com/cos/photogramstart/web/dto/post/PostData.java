package com.cos.photogramstart.web.dto.post;

import com.cos.photogramstart.domain.post.entity.Post;

import com.cos.photogramstart.web.dto.comment.CommentResponseDto;
import lombok.Data;
import java.util.List;


@Data
public class PostData {

    private String profileImageUrl;
    private String caption;
    private int userId;
    private String postImageUrl;
    private String username;
    private int imageId;
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
