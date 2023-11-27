package com.cos.photogramstart.domain.post.entity;

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
}
