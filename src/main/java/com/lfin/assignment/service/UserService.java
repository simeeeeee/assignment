package com.lfin.assignment.service;

import com.lfin.assignment.domain.entity.User;
import com.lfin.assignment.domain.vo.UserExceptPasswordVO;
import com.lfin.assignment.domain.vo.UserVO;
import com.lfin.assignment.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
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
     * 비밀번호는 조회 목록에 나오지 않도록 mapper활용
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

    public void update(Long id, UserVO userVO){
        User user = userRepository.findById(id).orElseThrow();

            if (!StringUtils.isEmpty(userVO.getEmail())) {
                throw new RuntimeException("이메일은 변경할 수 없습니다.");
            }
            if (!StringUtils.isEmpty(userVO.getPassword())) {
                String newPassword = userVO.hashPassword(userVO.getPassword());
                user.setPassword(newPassword);
            }

            if (!StringUtils.isEmpty(userVO.getTel())) {
                user.setTel(userVO.getTel());
            }

            if (!StringUtils.isEmpty(userVO.getName())) {
                user.setName(userVO.getName());
            }
            userRepository.save(user);

    }

    /**
     * Email과 password를 입력받아 데이터베이스에 저장된 정보와 일치하는지 check
     * 일치하면 true를 return, 일치하지 않을 시, 예외 발생
     * @param userVO
     * @return
     */
    public boolean checkUserInfo(UserVO userVO){
        //기존 암호와 동일한지 확인
        User user = userRepository.findByEmail(userVO.getEmail()).orElseThrow();
        String currentPassword = user.getPassword();
        String inputPassword = userVO.hashPassword(userVO.getPassword());
        if(currentPassword.equals(inputPassword)){
            return true;
        }else throw new RuntimeException("기존의 비밀번호와 일치하지 않습니다.");
    }

    public void deletedById(Long id){
        userRepository.deleteById(id);
    }
}
