package com.medjamal.ouazani.springsecuritydemo.security;

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

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private UserService userService;

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

        AppUser newUser = new AppUser(
                signUpRequest.getName(),
                signUpRequest.getUsername(),
                signUpRequest.getPassword()
        );

        return userService.save(newUser);
    }
}
