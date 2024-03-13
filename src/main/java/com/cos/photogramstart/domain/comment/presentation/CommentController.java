package com.cos.photogramstart.domain.comment.presentation;

import com.cos.photogramstart.domain.comment.application.CommentResult;
import com.cos.photogramstart.domain.comment.presentation.request.CommentRequest;
import com.cos.photogramstart.domain.comment.application.CommentService;
import com.cos.photogramstart.global.result.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.cos.photogramstart.global.result.ResultCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<ResultResponse> commentService(@RequestBody @Valid CommentRequest commentRequest) {
        commentService.write(commentRequest.toCommand());
        return ResponseEntity.ok(ResultResponse.of(COMMENT_POST_SUCCESS));
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<?> commentDelete(@PathVariable int id) {
        commentService.delete(id);
        return ResponseEntity.ok(ResultResponse.of(COMMENT_DELETE_SUCCESS));
    }

    @GetMapping("/comment/{postId}")
    public ResponseEntity<?> getComments(@PathVariable Long postId) {
        List<CommentResult> results = commentService.findByPostId(postId);
        return ResponseEntity.ok(ResultResponse.of(COMMENT_GET_SUCCESS,results));
    }

}
