package com.jane.springboot.todos.repository;

import com.jane.springboot.todos.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);


    @Query("SELECT COUNT(u) from User u JOIN u.authorities a where a.authority = 'ROLE_ADMIN'")
    long countAdmins();
}
