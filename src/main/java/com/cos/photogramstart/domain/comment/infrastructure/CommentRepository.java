package com.cos.photogramstart.domain.comment.infrastructure;

import com.cos.photogramstart.domain.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Integer> , CommentRepositoryCustom {

}
