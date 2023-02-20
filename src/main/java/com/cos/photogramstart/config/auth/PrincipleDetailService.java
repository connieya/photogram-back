package com.cos.photogramstart.config.auth;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
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
        System.out.println("아이디 => "+ username);
        User userEntity = userRepository.findByUsername(username);
        System.out.println("로그인 요청 => " +userEntity);
//        User userEntity = userRepository.findByUsername(username).orElseThrow(() -> {
//            throw new CustomApiException("해당 아이디를 찾을 수 없습니다.");
//        });


        if (userEntity == null){
            System.out.println("@@@@@@@@");
            return null;
        }
        return new PrincipalDetails(userEntity);
    }
}
