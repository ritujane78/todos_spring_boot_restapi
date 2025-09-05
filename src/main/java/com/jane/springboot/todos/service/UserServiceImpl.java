package com.jane.springboot.todos.service;

import com.jane.springboot.todos.entity.Authority;
import com.jane.springboot.todos.entity.User;
import com.jane.springboot.todos.repository.UserRepository;
import com.jane.springboot.todos.response.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserInfo() throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated() ||
                authentication.getPrincipal().equals("anonymousUser")){
            throw new AccessDeniedException("Access is denied. Authentication is required");
        }

        User user = (User) authentication.getPrincipal();
        return new UserResponse(
                user.getId(),
                user.getFirstName() + " "+ user.getLastName(),
                user.getEmail(),
                user.getAuthorities().stream().map(auth -> (Authority) auth).toList()
        );
    }

    @Override
    public void deleteUser() throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")){
            throw new AccessDeniedException("Authntication is required");
        }

        User user = (User) authentication.getPrincipal();

        if(isLastAdmin(user)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Admin cannot delete itself");
        }
        userRepository.delete(user);
    }

    private boolean isLastAdmin(User user) {
        boolean isAdmin = user.getAuthorities().stream().anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));

        if(isAdmin){
            long adminCount = userRepository.countAdmins();
            return adminCount <= 1;
        }

        return false;
    }
}
