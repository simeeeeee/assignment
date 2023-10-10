package com.lfin.assignment.service;

import com.lfin.assignment.common.TestDataConfiguration;
import com.lfin.assignment.domain.vo.UserVO;
import com.lfin.assignment.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("test")
@SpringBootTest(classes = {TestDataConfiguration.class})
class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void init() throws Exception{
    }

    @Test
    void createUser(){
        String email = "gildong@naver.com";
        UserVO userVO = new UserVO();
        userVO.setName("홍길동");
        userVO.setPassword("1234");
        userVO.setEmail(email);
        userVO.setTel("010-1234-1234");

        userService.createUser(userVO);

        boolean exsit = userRepository.existsByEmail(email);

        assertTrue(exsit);

    }

    @Test
    void existEmail() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            String email = "whdk2340@naver.com";
            UserVO userVO = new UserVO();
            userVO.setName("홍길동");
            userVO.setPassword("1234");
            userVO.setEmail(email);
            userVO.setTel("010-1234-1234");

            userService.createUser(userVO);

            boolean exsit = userRepository.existsByEmail(email);

            assertTrue(exsit);

        });
    }
}