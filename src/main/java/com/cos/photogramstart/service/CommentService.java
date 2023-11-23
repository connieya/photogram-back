package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.comment.CommentRepository;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.exception.CustomApiException;
import com.cos.photogramstart.web.dto.comment.CommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    @Transactional
    public Comment write(String content , int imageId , int userId)
    {
        User userEntity = userRepository.findById(userId).orElseThrow(() -> {
            throw new CustomApiException("유저 아이디를 찾을 수 없습니다.");
        });
        Image image = imageRepository.findById(imageId).orElseThrow(() -> {
            throw new CustomApiException("해당 게시글을 찾을 수 없습니다.");
        });

        Comment comment = Comment.addComment(content, image, userEntity);
        return commentRepository.save(comment);
    }

    @Transactional
    public void delete(int id) {
        try{
        commentRepository.deleteById(id);
        }catch(Exception e){
            throw new CustomApiException(e.getMessage());
        }
    }

    @Transactional
    public List<CommentResponseDto> findByImageId(int imageId) {
        return commentRepository.findByImageId(imageId);
    }
}
