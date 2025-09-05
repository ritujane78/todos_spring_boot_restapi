package com.jane.springboot.todos.controller;


import com.jane.springboot.todos.request.AuthenticationRequest;
import com.jane.springboot.todos.request.RegisterRequest;
import com.jane.springboot.todos.response.AuthenticationResponse;
import com.jane.springboot.todos.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication REST Api Endpoints", description = "Operations related to register & login")
@RequestMapping("/api/auth")
@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Operation(summary = "Registr a user", description = "Create a new user in database.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public void registerUser(@Valid @RequestBody RegisterRequest registerRequest) throws Exception{
        authenticationService.register(registerRequest);
    }

    @Operation(summary = "login a user", description = "authenticate if the provided email and password is of a user")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public AuthenticationResponse login(@Valid @RequestBody AuthenticationRequest authRequest){
        return authenticationService.login(authRequest);
    }
}
