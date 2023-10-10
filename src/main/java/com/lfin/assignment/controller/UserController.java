package com.lfin.assignment.controller;

import com.lfin.assignment.common.utils.ApiResult;
import com.lfin.assignment.domain.vo.UserVO;
import com.lfin.assignment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResult> createUser(@RequestBody UserVO userVO){
        userService.createUser(userVO);
        return  ResponseEntity.ok().body(new ApiResult());
    }


}
