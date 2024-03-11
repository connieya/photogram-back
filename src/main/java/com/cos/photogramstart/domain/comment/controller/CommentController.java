package com.cos.photogramstart.domain.comment.controller;

import com.cos.photogramstart.global.config.security.auth.PrincipalDetails;
import com.cos.photogramstart.domain.comment.service.CommentService;
import com.cos.photogramstart.global.result.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.cos.photogramstart.global.result.ResultCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<ResultResponse> commentService(
            @Valid @RequestBody CommentWriteDto dto, BindingResult bindingResult,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
//        Comment comment = commentService.write(dto.getContent(), dto.getImageId(), principalDetails.getUser().getId());
        return ResponseEntity.ok(ResultResponse.of(COMMENT_POST_SUCCESS));
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<?> commentDelete(@PathVariable int id) {
        commentService.delete(id);
        return ResponseEntity.ok(ResultResponse.of(COMMENT_DELETE_SUCCESS));
    }

}
