package com.medjamal.ouazani.springsecuritydemo.services;

import com.medjamal.ouazani.springsecuritydemo.entities.AppUser;
import com.medjamal.ouazani.springsecuritydemo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public AppUser save(AppUser appUser){
        appUser.setPassword(passwordEncoder().encode(appUser.getPassword()));
        return userRepository.save(appUser);
    }

    public AppUser findUserByUserName(String username){
        return userRepository.findByUsername(username);
    }

    @Bean
    private PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public List<AppUser> getAll() {
        return userRepository.findAll();
    }
}
