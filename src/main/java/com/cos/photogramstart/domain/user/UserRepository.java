package com.cos.photogramstart.domain.user;


import com.cos.photogramstart.web.dto.auth.UserInfo;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    // jpa query method
//    @EntityGraph(attributePaths = "images")
//   Optional<User> findByUsername(String username);
    Optional<User> findByUsername(String username);
//   Optional<UserInfo> findById(int id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
