package com.lfin.assignment.service;

import com.lfin.assignment.common.TestDataConfiguration;
import com.lfin.assignment.domain.entity.User;
import com.lfin.assignment.domain.vo.UserExceptPasswordVO;
import com.lfin.assignment.domain.vo.UserVO;
import com.lfin.assignment.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Test
    void findAll(){
        Pageable pageable = PageRequest.of(0,3);
        Page<UserExceptPasswordVO> userPage = userService.findAll(pageable);

        UserExceptPasswordVO userExceptPasswordVO = userPage.getContent().get(0);

        assertEquals(userPage.getSize(), 3);
        assertEquals(userExceptPasswordVO.getEmail(), "whdk2340@naver.com");
        assertEquals(userExceptPasswordVO.getName(), "gildong");
        assertEquals(userExceptPasswordVO.getTel(), "010-1123-1234");
    }

    @Test
    void findById(){
        Long id = 3L;

        UserExceptPasswordVO byId = userService.findById(id);

        assertEquals(byId.getUserId(), id);
        assertEquals(byId.getName(), "gildong");
        assertEquals(byId.getEmail(), "whdk237@naver.com");
        assertEquals(byId.getTel(), "010-2345-3434");
    }
}