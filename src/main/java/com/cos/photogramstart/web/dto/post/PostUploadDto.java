package com.cos.photogramstart.web.dto.post;

import com.cos.photogramstart.domain.post.entity.Post;
import com.cos.photogramstart.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUploadDto implements Serializable {
    private MultipartFile file;
    private String caption;

    private String baseUrl;

    public PostUploadDto(MultipartFile file, String caption) {
        this.file = file;
        this.caption = caption;
    }

    public Post toEntity(String postImgUrl , User user , String baseUrl){
        return Post.builder()
                .caption(caption)
                .postImageUrl(postImgUrl)
                .user(user)
                .baseUrl(baseUrl)
                .build();
    }
}
