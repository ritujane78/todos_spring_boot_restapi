package com.jane.springboot.todos.service;

import com.jane.springboot.todos.response.UserResponse;

import java.nio.file.AccessDeniedException;

public interface UserService {
    UserResponse getUserInfo() throws AccessDeniedException;
}
