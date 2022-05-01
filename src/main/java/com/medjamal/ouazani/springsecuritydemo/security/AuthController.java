package com.medjamal.ouazani.springsecuritydemo.security;

import com.medjamal.ouazani.springsecuritydemo.entities.Role;
import com.medjamal.ouazani.springsecuritydemo.helpers.Constants;
import com.medjamal.ouazani.springsecuritydemo.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signin")
    public JwtResponse signIn(@RequestBody SignInRequest signInRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = userService.loadUserByUsername(signInRequest.getUsername());

        String token = tokenUtil.generateToken(userDetails);

        return new JwtResponse(token);
    }

    @PostMapping("/signup")
    public AppUser signUp(@RequestBody SignUpRequest signUpRequest) throws Exception {
        if(userService.findUserByUserName(signUpRequest.getUsername()) != null){
            throw new Exception("User already exist");
        }

        // Assign ROLE_USER role to the new user
        Role userRole = roleService.getRoleByName(Constants.ROLE_USER);
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);

        AppUser newUser = new AppUser(
                signUpRequest.getName(),
                signUpRequest.getUsername(),
                signUpRequest.getPassword(),
                roles
        );

        return userService.save(newUser);
    }
}
