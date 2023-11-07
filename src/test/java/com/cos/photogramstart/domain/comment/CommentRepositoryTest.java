package com.cos.photogramstart.domain.comment;

import com.cos.photogramstart.web.dto.comment.CommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepositoryImpl commentRepository;

    @Test
    public void Test() {
        //given
        int imageId = 10;
        //when
        List<CommentResponseDto> result = commentRepository.findByImageId(imageId);
        //then
        assertThat(result.size()).isEqualTo(2);
        for (CommentResponseDto commentResponseDto : result) {
            System.out.println("commentDto = " + commentResponseDto);
        }

    }

}