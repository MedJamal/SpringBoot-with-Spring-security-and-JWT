package com.medjamal.ouazani.springsecuritydemo.repositories;

import com.medjamal.ouazani.springsecuritydemo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
