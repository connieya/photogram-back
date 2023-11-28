package com.cos.photogramstart.global.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Lob;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Image {

    @Lob
    private String imageUrl;
    private String imageType;
    private String imageUUID;
    private String imageName;

    public void setImageUrl(String baseUrl) {
        this.imageUrl = baseUrl;
    }
}
