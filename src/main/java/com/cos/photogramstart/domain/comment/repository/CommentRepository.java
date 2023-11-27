package com.cos.photogramstart.domain.comment.repository;

import com.cos.photogramstart.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Integer> , CommentRepositoryCustom {

}
