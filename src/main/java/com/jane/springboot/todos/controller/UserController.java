package com.jane.springboot.todos.controller;

import com.jane.springboot.todos.request.PasswordUpdateRequest;
import com.jane.springboot.todos.response.UserResponse;
import com.jane.springboot.todos.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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

    @Operation(summary = "User information", description = "Get Current user's info.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/info")
    public UserResponse getUserInfo() throws AccessDeniedException {
        return userService.getUserInfo();
    }

    @Operation(summary = "Delete user", description = "Delete current user account")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    public void deleteUser() throws AccessDeniedException {
        userService.deleteUser();
    }


    @Operation(summary = "Password update", description = "Change user password after verification.")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/password")
    public void updatePassword(@Valid @RequestBody PasswordUpdateRequest passwordUpdateRequest) throws Exception{
        userService.updatePassword(passwordUpdateRequest);
    }

}
