package com.medjamal.ouazani.springsecuritydemo.repositories;

import com.medjamal.ouazani.springsecuritydemo.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    public AppUser findByUsername(String username);
}
