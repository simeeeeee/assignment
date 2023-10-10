package com.lfin.assignment.service;

import com.lfin.assignment.domain.entity.User;
import com.lfin.assignment.domain.vo.UserVO;
import com.lfin.assignment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void createUser(UserVO userVO){
        //아이디(email) 중복 검사
        if(!userRepository.existsByEmail(userVO.getEmail())){
            User user = User.createUser(userVO);
            userRepository.save(user);
        }else throw new RuntimeException("이미 존재하는 이메일입니다.");
    }
}
