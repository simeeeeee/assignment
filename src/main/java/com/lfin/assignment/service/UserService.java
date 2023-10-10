package com.lfin.assignment.service;

import com.lfin.assignment.domain.entity.User;
import com.lfin.assignment.domain.vo.UserExceptPasswordVO;
import com.lfin.assignment.domain.vo.UserVO;
import com.lfin.assignment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 전체 user정보 페이징하여 조회
     * @param pageable
     * @return
     */
    public Page<UserExceptPasswordVO> findAll(Pageable pageable){
        List<User> all = userRepository.findAll();
        List<UserExceptPasswordVO> collect = all.stream().map(User::convertToVo).collect(Collectors.toList());
        return new PageImpl<>(collect, pageable, all.size());
    }

    public UserExceptPasswordVO findById(Long id){
        return userRepository.findById(id).orElseThrow().convertToVo();
    }

}
