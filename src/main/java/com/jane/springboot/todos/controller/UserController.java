package com.jane.springboot.todos.controller;

import com.jane.springboot.todos.response.UserResponse;
import com.jane.springboot.todos.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@Tag(name = "User REST API Endpoints", description = "Operations related to info about current user.")
@RestController
@RequestMapping("/api/login")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info")
    public UserResponse getUserInfo() throws AccessDeniedException {
        return userService.getUserInfo();
    }

    @DeleteMapping
    public void deleteUser() throws AccessDeniedException {
        userService.deleteUser();
    }

}
