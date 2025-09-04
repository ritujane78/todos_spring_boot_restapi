package com.jane.springboot.todos.service;

import com.jane.springboot.todos.request.AuthenticationRequest;
import com.jane.springboot.todos.request.RegisterRequest;
import com.jane.springboot.todos.response.AuthenticationResponse;
import org.springframework.security.authentication.AuthenticationManager;

public interface AuthenticationService {
    void register(RegisterRequest registerRequest) throws Exception;

    AuthenticationResponse login(AuthenticationRequest request);
}
