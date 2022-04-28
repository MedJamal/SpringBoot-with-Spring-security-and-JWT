package com.medjamal.ouazani.springsecuritydemo.controllers;

import com.medjamal.ouazani.springsecuritydemo.security.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/authuser")
    public AppUser authuser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = (AppUser)authentication.getPrincipal();
        return appUser;
    }

    @GetMapping("/publicRoute")
    public String publicRoute(){
        return "Public Route";
    }
}
