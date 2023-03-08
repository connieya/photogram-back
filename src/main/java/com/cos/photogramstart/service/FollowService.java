package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.folllow.FollowRepository;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.follow.FollowDto;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository subscribeRepository;
    private final UserRepository userRepository;
    private final EntityManager em;

    // 팔로잉 리스트
    @Transactional(readOnly = true)
    public List<FollowDto> followingList(int principalId, int pageUserId) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.id , u.username , u.profileImageUrl , ");
        sb.append("if ((SELECT 1 FROM subscribe WHERE fromUserId = ? AND toUserId = u.id),1 ,0) subscribeState , ");
        sb.append("if ((? =u.id) ,1,0) equalUserState ");
        sb.append("FROM user u INNER JOIN subscribe s ");
        sb.append("ON u.id = s.toUserId ");
        sb.append("WHERE s.fromUserId = ? ");

        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, principalId)
                .setParameter(2, principalId)
                .setParameter(3, pageUserId);

        JpaResultMapper result = new JpaResultMapper();
        List<FollowDto> subscribeDtos = result.list(query, FollowDto.class);
        return  subscribeDtos;
    }

    // 팔로워 리스트
    @Transactional(readOnly = true)
    public List<FollowDto> followerList(int principalId, int pageUserId) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.id , u.username , u.profileImageUrl , ");
        sb.append("if ((SELECT 1 FROM subscribe WHERE fromUserId = ? AND toUserId = u.id),1 ,0) subscribeState , ");
        sb.append("if ((? =u.id) ,1,0) equalUserState ");
        sb.append("FROM user u INNER JOIN subscribe s ");
        sb.append("ON u.id = s.fromUserId ");
        sb.append("WHERE s.toUserId = ? ");

        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, principalId)
                .setParameter(2, principalId)
                .setParameter(3, pageUserId);

        JpaResultMapper result = new JpaResultMapper();
        List<FollowDto> subscribeDtos = result.list(query, FollowDto.class);
        return  subscribeDtos;
    }


    @Transactional
    public void follow(int fromUserId , int toUserId){
        try {
            subscribeRepository.mFollow(fromUserId,toUserId);
        }catch (Exception e){
            throw new CustomApiException("이미 구독을 하였습니다.");
        }

    }

    @Transactional
    public void unfollow(int fromUserId, int toUserId){
        subscribeRepository.mUnfollow(fromUserId,toUserId);
    }

}
