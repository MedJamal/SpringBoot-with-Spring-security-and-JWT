package com.medjamal.ouazani.springsecuritydemo.services;

import com.medjamal.ouazani.springsecuritydemo.entities.Role;
import com.medjamal.ouazani.springsecuritydemo.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }

    public void save(Role role){
        roleRepository.save(role);
    }

    public void saveAll(Set<Role> roles){
        roleRepository.saveAll(roles);
    }

    public Role getRoleByName(String roleUser) {
        return roleRepository.findByName(roleUser);
    }
}
