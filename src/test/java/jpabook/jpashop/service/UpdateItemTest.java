package jpabook.jpashop.service;

import jpabook.jpashop.domain.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UpdateItemTest {

    @Autowired
    EntityManager em;

    @Test
    public void updateTest() throws Exception{
        //given
        em.find(Book.class,1L);
        //when

        //then
    }
}
