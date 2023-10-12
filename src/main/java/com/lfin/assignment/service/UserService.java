package com.lfin.assignment.service;

import com.lfin.assignment.common.exceptions.ExistingValueException;
import com.lfin.assignment.common.exceptions.NotChangingValueException;
import com.lfin.assignment.common.exceptions.NotMatchingValueException;
import com.lfin.assignment.common.exceptions.ResourceNotFoundException;
import com.lfin.assignment.domain.entity.User;
import com.lfin.assignment.domain.vo.UserExceptPasswordVO;
import com.lfin.assignment.domain.vo.UserVO;
import com.lfin.assignment.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(UserVO userVO){
        //아이디(email) 중복 검사
        if(!userRepository.existsByEmail(userVO.getEmail())){
            User user = User.createUser(userVO);
            userRepository.save(user);
        }else throw new ExistingValueException();
    }

    /**
     * 전체 user정보 페이징하여 조회
     * 비밀번호는 조회 목록에 나오지 않도록 mapper활용
     */
    public Page<UserExceptPasswordVO> findAll(Pageable pageable){
        Page<User> all = userRepository.findAll(pageable);
        List<UserExceptPasswordVO> collect = all.stream().map(User::convertToVo).collect(Collectors.toList());
        return new PageImpl<>(collect, pageable, all.getTotalElements());
    }

    public UserExceptPasswordVO findById(Long id){
        return userRepository.findById(id).orElseThrow(ResourceNotFoundException::new).convertToVo();
    }

    public void update(Long id, UserVO userVO){
        User user = userRepository.findById(id).orElseThrow();
            //email값을 아이디처럼 사용하므로 변경하지 못하게 input값 넣었을 때 exception
            if (!StringUtils.isEmpty(userVO.getEmail())) {
                throw new NotChangingValueException();
            }
            if (!StringUtils.isEmpty(userVO.getPassword())) {
                String newPassword = userVO.encodeBcrypt(userVO.getPassword());
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
     */
    public boolean checkPassword(UserVO userVO){
        //기존 암호와 동일한지 확인
        User user = userRepository.findByEmail(userVO.getEmail()).orElseThrow(ResourceNotFoundException::new);
        if(passwordEncoder.matches(userVO.getPassword(), user.getPassword())){
            return true;
        }else throw new NotMatchingValueException();
    }

    public void deletedById(Long id){
        userRepository.deleteById(id);
    }
}
