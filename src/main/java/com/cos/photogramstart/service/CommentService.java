package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.comment.CommentRepository;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Comment write(String content , int imageId , int userId)
    {
        Image image = new Image();
        User user = new User();
        image.setId(imageId);
        user.setId(userId);
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setImage(image);
        comment.setUser(user);
        return commentRepository.save(comment);
    }

    @Transactional
    public void delete() {

    }
}
