package com.cos.photogramstart;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
public class QuerydslTest {


    @PersistenceContext
    JPAQueryFactory queryFactory;
    EntityManager em;

    @BeforeEach()
    public void before(){
        queryFactory = new JPAQueryFactory(em);
    }

    @Test
    public void follow(){

    }

}
