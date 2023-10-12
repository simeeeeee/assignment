package com.lfin.assignment.domain.entity;

import com.lfin.assignment.common.TestDataConfiguration;
import com.lfin.assignment.domain.vo.UserExceptPasswordVO;
import com.lfin.assignment.domain.vo.UserVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(classes = {TestDataConfiguration.class})
class UserTest {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void createUser(){
        UserVO userVO = new UserVO();
        userVO.setName("홍길동");
        userVO.setPassword("1234");
        userVO.setEmail("gildong@naver.com");
        userVO.setTel("010-1234-1234");
        String s = userVO.encodeBcrypt(userVO.getPassword());

        User user = User.createUser(userVO);
        assertEquals(userVO.getName(), user.getName());
        assertEquals(userVO.getEmail(), user.getEmail());
        assertEquals(userVO.getTel(), user.getTel());
        assertTrue(passwordEncoder.matches(userVO.getPassword(), user.getPassword()));
    }

    @Test
    void convertToVo(){
        User gildong = User.builder()
                .tel("010-234-2344")
                .name("고길동")
                .email("whkd23@gmail.com")
                .password("1245")
                .build();

        UserExceptPasswordVO userExceptPasswordVO = gildong.convertToVo();

        assertEquals(userExceptPasswordVO.getEmail(), gildong.getEmail());
        assertEquals(userExceptPasswordVO.getName(), gildong.getName());
        assertEquals(userExceptPasswordVO.getTel(), gildong.getTel());
    }
}