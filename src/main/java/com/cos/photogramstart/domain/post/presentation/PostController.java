package com.cos.photogramstart.domain.post.presentation;

import com.cos.photogramstart.domain.post.application.PostDetail;
import com.cos.photogramstart.domain.post.application.PostLikeService;
import com.cos.photogramstart.domain.post.application.PostResult;
import com.cos.photogramstart.domain.post.application.PostService;
import com.cos.photogramstart.global.result.ResultCode;
import com.cos.photogramstart.global.result.ResultResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.cos.photogramstart.global.result.ResultCode.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
@Slf4j
public class PostController {

    private final PostService postService;
    private final PostLikeService postLikeService;



    @ApiOperation(value = "게시글 조회")
    @GetMapping
    public List<PostResult> selectPost() {
        return postService.findAll();
    }

    @ApiOperation(value = "게시글 상세 조회")
    @GetMapping("/{postId}")
    public ResponseEntity<ResultResponse> findPost(@PathVariable Long postId){
        PostDetail postDetail = postService.findPost(postId);
        return ResponseEntity.ok(ResultResponse.of(POST_DETAIL_GET_SUCCESS,postDetail));
    }

    @ApiOperation(value = "게시글 삭제")
    @DeleteMapping("/{postId}")
    public ResponseEntity<ResultResponse> delete(@PathVariable Long postId) {
        postService.delete(postId);
        return ResponseEntity.ok(ResultResponse.of(POST_DELETE_SUCCESS));
    }




    @ApiOperation(value = "게시물 업로드", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping
    public ResponseEntity<ResultResponse> uploadPost(@ModelAttribute PostUploadRequest request) throws IOException {
        System.out.println("request.getFile() = " + request.getFile());
        postService.upload(request.toCommand(), request.getFile());
        return ResponseEntity.ok(ResultResponse.of(CREATE_POST_SUCCESS));
    }

    @ApiOperation(value = "게시글 좋아요")
    @PostMapping("/likes/{postId}")
    public ResponseEntity<?> like(@PathVariable Long postId ){
        postLikeService.like(postId);
        return ResponseEntity.ok(ResultResponse.of(LIKE_POST_SUCCESS));
    }

    @ApiOperation(value = "게시글 좋아요 취소")
    @DeleteMapping("/likes/{postId}")
    public ResponseEntity<?> unLike(@PathVariable Long postId){
        postLikeService.unLike(postId);
        return ResponseEntity.ok(ResultResponse.of(UN_LIKE_POST_SUCCESS));
    }



}
