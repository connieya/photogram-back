package com.cos.photogramstart.domain.user.fixture;

import com.cos.photogramstart.domain.user.domain.User;

import java.time.LocalDateTime;

public class UserFixture {

    public static final User PARK =   User.create(
            1L
            , "geonhee"
            , "1234"
            , "박건희"
            , "gunny6026@naver.com"
    );

    public static final User CONY =   User.create(
            2L
            , "cony"
            , "1234"
            , "코니"
            , "cony6@naver.com"
    );
}
