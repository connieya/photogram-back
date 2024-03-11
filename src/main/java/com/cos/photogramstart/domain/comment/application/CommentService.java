package com.cos.photogramstart.domain.comment.application;

import com.cos.photogramstart.domain.comment.domain.Comment;
import com.cos.photogramstart.domain.comment.infrastructure.CommentRepository;
import com.cos.photogramstart.domain.post.domain.Post;
import com.cos.photogramstart.domain.post.infrastructure.PostRepository;
import com.cos.photogramstart.domain.user.domain.User;
import com.cos.photogramstart.domain.user.infrastructure.UserRepository;
import com.cos.photogramstart.global.error.exception.EntityNotFoundException;
import com.cos.photogramstart.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cos.photogramstart.global.error.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final AuthUtil authUtil;

    @Transactional
    public void write(CommentCommand commentCommand) {
        User loginUser = authUtil.getLoginUser();
        Post post = postRepository.findById(commentCommand.getImageId())
                .orElseThrow(() -> new EntityNotFoundException(POST_NOT_FOUND));

        Comment comment = Comment.create(commentCommand.getContent(), post, loginUser);
        commentRepository.save(comment);
    }

    @Transactional
    public void delete(int id) {
        commentRepository.deleteById(id);
    }

    @Transactional
    public List<CommentResponseDto> findByImageId(int imageId) {
        return commentRepository.findByImageId(imageId);
    }
}
