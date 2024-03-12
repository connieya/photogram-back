package com.cos.photogramstart.global.util;

import com.cos.photogramstart.domain.user.domain.Image;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

class ImageUtilTest {


    @DisplayName("멀티파트 파일을 이미지로 변환 한다. ")
    @Test
    void convertMultipartToImage() throws IOException {
        // given
        final MockMultipartFile file = new MockMultipartFile(
                "file",
                "cony.jpg",
                "image/jpeg",
                new FileInputStream("./src/test/resources/static/images/cony.jpg")
        );
        // when
        String UUID = "aaaa-bbbbb-cccc";
        Image image = ImageUtil.convertMultipartToImage(file,UUID);
        //then
        assertThat(image.getImageUUID()).isEqualTo("aaaa-bbbbb-cccc");
        assertThat(image.getImageName()).isEqualTo("cony");
        assertThat(image.getImageType()).isEqualTo("JPG");

    }

}