package com.lfin.assignment.domain.entity;

import com.lfin.assignment.domain.vo.UserVO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void createUser(){
        UserVO userVO = new UserVO();
        userVO.setName("홍길동");
        userVO.setPassword("1234");
        userVO.setEmail("gildong@naver.com");
        userVO.setTel("010-1234-1234");

        User user = User.createUser(userVO);
        assertEquals(userVO.getName(), user.getName());
        assertEquals(userVO.getEmail(), user.getEmail());
        assertEquals(userVO.getTel(), user.getTel());
        assertEquals(userVO.hashPassword(userVO.getPassword()), user.getPassword());
    }
}