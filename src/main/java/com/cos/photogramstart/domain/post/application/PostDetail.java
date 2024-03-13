package com.cos.photogramstart.domain.post.application;

import com.cos.photogramstart.domain.comment.application.CommentResult;
import com.cos.photogramstart.domain.post.domain.Post;
import com.cos.photogramstart.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostDetail {

    private Long postId;
    private String postImageUrl;
    private String caption;
    private String location;
    private String profileImageUrl;
    private String username;
    private List<CommentResult> commentResults;


    @Builder
    private PostDetail(Long postId, String postImageUrl, String caption, String location, String profileImageUrl, String username, List<CommentResult> commentResults) {
        this.postId = postId;
        this.postImageUrl = postImageUrl;
        this.caption = caption;
        this.location = location;
        this.profileImageUrl = profileImageUrl;
        this.username = username;
        this.commentResults = commentResults;
    }

    public static PostDetail create(Post post) {
        User user = post.getUser();
        return PostDetail
                .builder()
                .postId(post.getId())
                .postImageUrl(post.getPostImage().getImage().getImageUrl())
                .caption(post.getCaption())
                .location(post.getLocation())
                .profileImageUrl(user.getImage().getImageUrl())
                .username(user.getUsername())
                .commentResults(
                        post.getComments()
                                .stream()
                                .map((comment)->new CommentResult(
                                        comment.getId()
                                        , comment.getContent()
                                        , comment.getUser().getId()
                                        , comment.getUser().getUsername()
                                ))
                                .collect(Collectors.toList())
                )
                .build();
    }

}
