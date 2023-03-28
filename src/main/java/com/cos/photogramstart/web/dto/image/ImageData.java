package com.cos.photogramstart.web.dto.image;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import lombok.Getter;

import java.util.List;

@Getter
public class ImageData {

    private Image image;
    private boolean likeState;
    private Long likeCount;
}
