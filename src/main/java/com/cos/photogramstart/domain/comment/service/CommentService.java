package com.cos.photogramstart.domain.comment.service;

import com.cos.photogramstart.domain.comment.entity.Comment;
import com.cos.photogramstart.domain.comment.repository.CommentRepository;
import com.cos.photogramstart.domain.post.domain.Post;
import com.cos.photogramstart.domain.post.infrastructure.PostRepository;
import com.cos.photogramstart.domain.user.domain.User;
import com.cos.photogramstart.domain.user.infrastructure.UserRepository;
import com.cos.photogramstart.global.error.exception.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public Comment write(String content , Long imageId , Long userId)
    {
        User userEntity = userRepository.findById(userId).orElseThrow(() -> {
            throw new CustomApiException("유저 아이디를 찾을 수 없습니다.");
        });
        Post image = postRepository.findById(imageId).orElseThrow(() -> {
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
