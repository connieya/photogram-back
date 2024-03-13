package com.cos.photogramstart.domain.post.domain;

import com.cos.photogramstart.domain.user.domain.Image;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
            @AttributeOverride(name = "imageUrl", column = @Column(name = "post_image_url")),
            @AttributeOverride(name = "imageType", column = @Column(name = "post_image_type")),
            @AttributeOverride(name = "imageUUID", column = @Column(name = "post_image_uuid")),
            @AttributeOverride(name = "imageName", column = @Column(name = "post_image_name")),
    })
    private Image image;

    public PostImage() {
        this.image = Image.init();
    }

    public PostImage(Image image) {
        this.image = image;
    }
}
