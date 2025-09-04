package com.jane.springboot.todos.service;

import com.jane.springboot.todos.request.RegisterRequest;

public interface AuthenticationService {
    void register(RegisterRequest registerRequest) throws Exception;
}
