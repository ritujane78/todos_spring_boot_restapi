package com.jane.springboot.todos.service;

import com.jane.springboot.todos.request.PasswordUpdateRequest;
import com.jane.springboot.todos.response.UserResponse;

import java.nio.file.AccessDeniedException;

public interface UserService{
    UserResponse getUserInfo() throws AccessDeniedException;
    void deleteUser() throws AccessDeniedException;

    void updatePassword(PasswordUpdateRequest passwordUpdateRequest) throws AccessDeniedException;
}
