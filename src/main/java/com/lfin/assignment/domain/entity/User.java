package com.lfin.assignment.domain.entity;

import com.lfin.assignment.domain.vo.UserExceptPasswordVO;
import com.lfin.assignment.domain.vo.UserVO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.modelmapper.ModelMapper;

@Entity
@Getter
@Setter
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
    private String tel;

    public static User createUser(UserVO userVO){
        return User.builder()
                .email(userVO.getEmail())
                .password(userVO.hashPassword(userVO.getPassword()))
                .name(userVO.getName())
                .tel(userVO.getTel())
                .build();
    }

    public UserExceptPasswordVO convertToVo(){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, UserExceptPasswordVO.class);
    }
}
