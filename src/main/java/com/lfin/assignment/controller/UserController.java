package com.lfin.assignment.controller;

import com.lfin.assignment.common.utils.ApiResult;
import com.lfin.assignment.domain.vo.UserVO;
import com.lfin.assignment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

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

    @GetMapping
    public ResponseEntity<ApiResult> findAll(final Pageable pageable){
        return ResponseEntity.ok().body(new ApiResult(userService.findAll(pageable)));
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResult> findById (@PathVariable Long id){
        return ResponseEntity.ok().body(new ApiResult(userService.findById(id)));
    }

}
