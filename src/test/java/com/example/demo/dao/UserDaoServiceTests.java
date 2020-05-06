package com.example.demo.dao;

import com.example.demo.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class UserDaoServiceTests{
    UserDaoService userDaoService = new UserDaoService();

    @Test
    public void testUserList(){
        List<User> list = userDaoService.getUserList();
        assertEquals(3, list.size());
        assertTrue(list.size() == 3, "초기사용자는 3명 ");
    }

    @Test
    public void test_사용자정보확인(){
        User user = userDaoService.getUserList().get(0);
        assertTrue(user.getId() == 1);
    }

    @Test
    public void test_사용자조회(){
        User user = userDaoService.getUser(Integer.valueOf(1));
        assertNotNull(user);
        assertEquals(1, user.getId(), "사용자의 id가 잘 못었습니다/.");
    }
}