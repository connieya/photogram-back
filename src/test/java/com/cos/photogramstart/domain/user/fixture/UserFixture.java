package com.cos.photogramstart.domain.user.fixture;

import com.cos.photogramstart.domain.user.domain.User;

import java.time.LocalDateTime;

public class UserFixture {

    public static final User PARK = new User(
            1L
            , "박건희"
            , "1234"
            , "geonhee"
            , "gunny6026@naver.com"
            , "www.naver.com"
            , "백엔드 개발자 입니다."
            , null
            , null
            , null
    );
}
