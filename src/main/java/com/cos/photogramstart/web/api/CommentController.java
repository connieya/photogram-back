package com.cos.photogramstart.web.api;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.comment.entity.Comment;
import com.cos.photogramstart.domain.comment.service.CommentService;
import com.cos.photogramstart.web.dto.RespDto;
import com.cos.photogramstart.web.dto.comment.CommentWriteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<?> commentService(
            @Valid @RequestBody CommentWriteDto dto, BindingResult bindingResult,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Comment comment = commentService.write(dto.getContent(), dto.getImageId(), principalDetails.getUser().getId());
        return new ResponseEntity<>(new RespDto<>(1, "댓글 쓰기 성공", comment), HttpStatus.CREATED);
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<?> commentDelete(@PathVariable int id) {
        commentService.delete(id);
        return new ResponseEntity<>(new RespDto<>(1, "댓글 삭제 성공", null), HttpStatus.OK);
    }

}
