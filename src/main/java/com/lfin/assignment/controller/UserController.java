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

    @PutMapping("{id}")
    public ResponseEntity<ApiResult> update(@PathVariable Long id, @RequestBody UserVO userVO){
        userService.update(id, userVO);
        return  ResponseEntity.ok().body(new ApiResult());
    }

    @PostMapping("/checkUserInfo")
    public ResponseEntity<ApiResult> checkUserInfo(@RequestBody UserVO userVO){
        return ResponseEntity.ok().body(new ApiResult(userService.checkUserInfo(userVO)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResult> deletedById(@PathVariable Long id){
        userService.deletedById(id);
        return ResponseEntity.ok().body(new ApiResult());
    }
}
