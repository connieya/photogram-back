package com.cos.photogramstart.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Lob;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Image {

    @Lob
    private String baseUrl;
    private String imageType;
    private String imageUUID;
    private String imageName;
}
