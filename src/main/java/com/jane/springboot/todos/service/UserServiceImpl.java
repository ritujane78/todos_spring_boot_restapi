package com.jane.springboot.todos.service;

import com.jane.springboot.todos.entity.Authority;
import com.jane.springboot.todos.entity.User;
import com.jane.springboot.todos.repository.UserRepository;
import com.jane.springboot.todos.request.PasswordUpdateRequest;
import com.jane.springboot.todos.response.UserResponse;
import com.jane.springboot.todos.util.FindAuthenticatedUser;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final FindAuthenticatedUser findAuthenticatedUser;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, FindAuthenticatedUser findAuthenticatedUser, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.findAuthenticatedUser = findAuthenticatedUser;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserInfo() throws AccessDeniedException {
        User user = findAuthenticatedUser.getAuthenticatedUser();
        return new UserResponse(
                user.getId(),
                user.getFirstName() + " "+ user.getLastName(),
                user.getEmail(),
                user.getAuthorities().stream().map(auth -> (Authority) auth).toList()
        );
    }

    @Override
    public void deleteUser() throws AccessDeniedException {
        User user = findAuthenticatedUser.getAuthenticatedUser();

        if(isLastAdmin(user)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Admin cannot delete itself");
        }
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public void updatePassword(PasswordUpdateRequest passwordUpdateRequest) throws AccessDeniedException {
        User user = findAuthenticatedUser.getAuthenticatedUser();

        if(!isCorrectPassword(user.getPassword(), passwordUpdateRequest.getOldPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Current password is incorrect.");
        }
        if(!isNewPasswordConfirmed(passwordUpdateRequest.getNewPassword(), passwordUpdateRequest.getNewPassword2())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New passwords do not match");
        }
        if(!isNewPasswordDifferent(passwordUpdateRequest.getOldPassword(), passwordUpdateRequest.getNewPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " New and old passwords are the same");
        }

        user.setPassword(passwordEncoder.encode(passwordUpdateRequest.getNewPassword()));
        userRepository.save(user);
    }

    private boolean isCorrectPassword(String userPassword, String oldPassword) {
//        System.out.println(userPassword + " " + oldPassword + " " + passwordEncoder.matches(userPassword,oldPassword));
        return passwordEncoder.matches(oldPassword, userPassword);
    }
    private boolean isNewPasswordConfirmed( String newPassword, String confirmedPassword){
        return newPassword.equals(confirmedPassword);
    }


    private boolean isNewPasswordDifferent(String oldPassword, String newPassword){
        return !oldPassword.equals(newPassword);
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
