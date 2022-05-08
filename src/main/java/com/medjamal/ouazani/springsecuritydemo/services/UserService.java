package com.medjamal.ouazani.springsecuritydemo.services;

import com.medjamal.ouazani.springsecuritydemo.security.AppUser;
import com.medjamal.ouazani.springsecuritydemo.entities.User;
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
        UserDetails userDetails = new AppUser(userRepository.findByUsername(username));
        return userDetails;
    }

    public User save(User user){
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findUserByUserName(String username){
        return userRepository.findByUsername(username);
    }

    @Bean
    private PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
}
