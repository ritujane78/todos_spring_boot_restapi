package com.jane.springboot.todos.service;

import com.jane.springboot.todos.entity.Authority;
import com.jane.springboot.todos.entity.User;
import com.jane.springboot.todos.repository.UserRepository;
import com.jane.springboot.todos.response.UserResponse;
import org.springframework.boot.rsocket.server.RSocketServerException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class AdminServiceImpl implements AdminService{
    private UserRepository userRepository;

    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(this::convertToUserResponse).toList();
    }

    @Override
    @Transactional
    public UserResponse promoteToAdmin(long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty() || user.get().getAuthorities().stream().anyMatch(auth -> "ROLE_ADMIN".equals(auth.getAuthority()))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User doesn't exist or is already an admin");
        }
        List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority("ROLE_EMPLOYEE"));
        authorities.add(new Authority("ROLE_ADMIN"));
        user.get().setAuthorities(authorities);

        User savedUser = userRepository.save(user.get());

        return convertToUserResponse(savedUser);
    }

    @Override
    public void deleteNonAdminUser(long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty() || user.get().getAuthorities().stream().anyMatch(auth -> "ROLE_ADMIN".equals(auth.getAuthority()))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The user doesn't xist or is already an admin");
        }
        userRepository.delete(user.get());
    }

    private UserResponse convertToUserResponse(User user){
        return new UserResponse(
                user.getId(),
                user.getFirstName() + " " + user.getLastName(),
                user.getEmail(),
                user.getAuthorities().stream().map(auth -> (Authority) auth).toList()
        );
    }
}
