package com.cos.photogramstart.config.auth;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipleDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUser 아이디 => "+ username);
        User userEntity = userRepository.findByUsername(username).orElseThrow(() -> {
            throw new CustomApiException("존재하지 않는 아이디 입니다.");
        });
        return new PrincipalDetails(userEntity);
    }
}
