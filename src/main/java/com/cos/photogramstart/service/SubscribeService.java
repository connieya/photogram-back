package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.subscribe.Subscribe;
import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;

    @Transactional
    public void subscribe(int fromUserId , int toUserId){
        try {
            subscribeRepository.mSubscribe(fromUserId,toUserId);
        }catch (Exception e){
            throw new CustomApiException("이미 구독을 하였습니다.");
        }

    }

    @Transactional
    public void unSubscribe(int fromUserId, int toUserId){
        subscribeRepository.mUnSubscribe(fromUserId,toUserId);
    }

}
