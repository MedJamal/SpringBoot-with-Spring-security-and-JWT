package com.medjamal.ouazani.springsecuritydemo.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medjamal.ouazani.springsecuritydemo.entities.Role;
import com.medjamal.ouazani.springsecuritydemo.entities.User;
import com.medjamal.ouazani.springsecuritydemo.helpers.Constants;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class AppUser implements UserDetails {

    private User user;

    public AppUser() {
    }

    public AppUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = this.user.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(Role role: roles){
            authorities.add(new SimpleGrantedAuthority(Constants.ROLE_PREFIX.concat(role.getName())));
        }
        return authorities;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
