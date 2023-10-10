package com.lfin.assignment.domain.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserExceptPasswordVO {
    private Long userId;
    private String email;
    private String name;
    private String tel;
}
