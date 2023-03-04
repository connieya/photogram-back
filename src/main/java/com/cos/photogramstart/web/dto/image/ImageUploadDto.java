package com.cos.photogramstart.web.dto.image;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageUploadDto implements Serializable {
    private MultipartFile file;
    private String caption;

    public Image toEntity(String postImgUrl , User user){
        return Image.builder()
                .caption(caption)
                .postImageUrl(postImgUrl)
                .user(user)
                .build();
    }
}
