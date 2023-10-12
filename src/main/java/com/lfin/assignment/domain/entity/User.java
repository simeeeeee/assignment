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
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    @Column(columnDefinition = "BINARY(16)")
    private UserId userId;
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
                .userId(UserId.create())
                .email(userVO.getEmail())
                .password(userVO.encodeBcrypt(userVO.getPassword()))
                .name(userVO.getName())
                .tel(userVO.getTel())
                .build();
    }

    public UserExceptPasswordVO convertToVo(){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, UserExceptPasswordVO.class);
    }
}
