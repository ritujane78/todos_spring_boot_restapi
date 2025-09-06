package com.jane.springboot.todos.util;

import com.jane.springboot.todos.entity.User;

import java.nio.file.AccessDeniedException;

public interface FindAuthenticatedUser {
    User getAuthenticatedUser() throws AccessDeniedException;
}
