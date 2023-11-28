package com.cos.photogramstart.domain.post.entity;

import com.cos.photogramstart.global.common.Image;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "post_images")
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_image_id")
    private Long id;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "baseUrl", column = @Column(name = "post_image_url")),
            @AttributeOverride(name = "imageType", column = @Column(name = "post_image_type")),
            @AttributeOverride(name = "imageUUID", column = @Column(name = "post_image_uuid")),
            @AttributeOverride(name = "imageName", column = @Column(name = "post_image_name")),
    })
    private Image image;
}
