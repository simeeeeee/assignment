package com.lfin.assignment.domain.entity;

import com.lfin.assignment.domain.vo.UserVO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @NotEmpty
    @Column(unique = true)
    private String email;
    @NotEmpty
    private String password;
    @NotEmpty
    private String name;
    @NotEmpty
    private String tel;

    public static User createUser(UserVO userVO){
        return User.builder()
                .email(userVO.getEmail())
                .password(userVO.hashPassword(userVO.getPassword()))
                .name(userVO.getName())
                .tel(userVO.getTel())
                .build();
    }
}
