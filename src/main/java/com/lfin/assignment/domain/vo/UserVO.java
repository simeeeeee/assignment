package com.lfin.assignment.domain.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
public class UserVO {
    private Long userId;
    private String email;
    private String password;
    private String name;
    private String tel;

    public String encodeBcrypt(String password) {
        // SHA-256 해시 알고리즘을 사용하여 비밀번호를 해싱
        return new BCryptPasswordEncoder().encode(password);
    }
}
