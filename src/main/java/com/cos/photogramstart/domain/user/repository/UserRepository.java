package com.cos.photogramstart.domain.user.repository;



import com.cos.photogramstart.domain.user.repository.querydsl.UserRepositoryQuerydsl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> , UserRepositoryQuerydsl {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
