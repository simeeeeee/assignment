package com.lfin.assignment.service;

import com.lfin.assignment.common.TestDataConfiguration;
import com.lfin.assignment.common.exceptions.ExistingValueException;
import com.lfin.assignment.common.exceptions.NotChangingValueException;
import com.lfin.assignment.common.exceptions.NotMatchingValueException;
import com.lfin.assignment.common.exceptions.ResourceNotFoundException;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
@Transactional
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
    void notExistEmail() {
        Assertions.assertThrows(ExistingValueException.class, () -> {
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

    @Test
    void update(){
        Long id = 3L;
        String name = "test_name";
        String tel = "010-2312-1111";
        String password = "123";
        UserVO userVO = new UserVO();
        userVO.setName(name);
        userVO.setTel(tel);
        String hashPassword = userVO.hashPassword(password);
        userVO.setPassword(password);

        userService.update(id, userVO);

        User user = userRepository.findById(id).orElseThrow();

        assertEquals(user.getUserId(), id);
        assertEquals(user.getTel(), tel);
        assertEquals(user.getName(), name);
        assertEquals(user.getPassword(), hashPassword);
    }

    @Test
    void updateWithName(){
        Long id = 3L;
        String name = "test_name";
        UserVO userVO = new UserVO();
        userVO.setName(name);

        userService.update(id, userVO);

        User user = userRepository.findById(id).orElseThrow();

        assertEquals(user.getUserId(), id);
        assertEquals(user.getName(), name);
    }

    @Test
    void updateWithTel(){
        Long id = 3L;
        String tel = "010-2312-1111";
        UserVO userVO = new UserVO();
        userVO.setTel(tel);

        userService.update(id, userVO);

        User user = userRepository.findById(id).orElseThrow();

        assertEquals(user.getUserId(), id);
        assertEquals(user.getTel(), tel);
    }

    @Test
    void updateWithPassword(){
        Long id = 3L;
        String password = "123";
        UserVO userVO = new UserVO();
        String hashPassword = userVO.hashPassword(password);
        userVO.setPassword(password);

        userService.update(id, userVO);

        User user = userRepository.findById(id).orElseThrow();

        assertEquals(user.getUserId(), id);
        assertEquals(user.getPassword(), hashPassword);
    }
    @Test
    void changeEmailException(){
        Assertions.assertThrows(NotChangingValueException.class, () -> {
            Long id = 3L;
            String name = "test_name";
            String tel = "010-2312-1111";
            String email = "test@test.com";
            UserVO userVO = new UserVO();
            userVO.setName(name);
            userVO.setTel(tel);
            userVO.setEmail(email);

            userService.update(id, userVO);

            User user = userRepository.findById(id).orElseThrow();

            assertEquals(user.getUserId(), id);
            assertEquals(user.getTel(), tel);
            assertEquals(user.getName(), name);
        });
    }

    @Test
    void checkUserInfo(){
        String email = "whdk2340@naver.com";
        String password = "12345";

        UserVO userVO = new UserVO();
        userVO.setEmail(email);
        userVO.setPassword(password);

        boolean b = userService.checkUserInfo(userVO);

        assertTrue(b);
    }

    @Test
    void notMatchingPassword(){
        Assertions.assertThrows(NotMatchingValueException.class, () ->{
            String email = "whdk2340@naver.com";
            String password = "123";

            UserVO userVO = new UserVO();
            userVO.setEmail(email);
            userVO.setPassword(password);

            boolean b = userService.checkUserInfo(userVO);

            assertTrue(b);
        });
    }

    @Test
    void notExistingEmail(){
        Assertions.assertThrows(ResourceNotFoundException.class, () ->{
            String email = "whdk23@naver.com";
            String password = "12345";

            UserVO userVO = new UserVO();
            userVO.setEmail(email);
            userVO.setPassword(password);

            boolean b = userService.checkUserInfo(userVO);

            assertTrue(b);
        });
    }

    @Test
    void deletedById(){
        Long id = 4L;
        User user = userRepository.findById(id).orElseThrow();
        String email = user.getEmail();
        boolean beforeExist = userRepository.existsByEmail(email);

        userService.deletedById(id);

        boolean afterExist = userRepository.existsByEmail(email);

        assertTrue(beforeExist);
        assertFalse(afterExist);
    }

    @Test
    void deletedByNotExistId(){
        Assertions.assertThrows(NoSuchElementException.class, () ->{
            Long id = 5L;
            User user = userRepository.findById(id).orElseThrow();
            String email = user.getEmail();
            boolean beforeExist = userRepository.existsByEmail(email);

            userService.deletedById(id);

            boolean afterExist = userRepository.existsByEmail(email);

            assertTrue(beforeExist);
            assertFalse(afterExist);
        });
    }
}