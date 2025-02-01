package com.example.notehub.auth;

import com.example.notehub.users.User;
import com.example.notehub.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @PostMapping("/login")
    public AuthResponse authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUserName(),authRequest.getPassword())
        );
        if(authentication.isAuthenticated()){
            String token = jwtService.generateToken(authRequest.getUserName());
            Long userId = userService.getUserByUserName(authRequest.getUserName()).getUserId();
            return new AuthResponse(userId,authRequest.getUserName(),token);
        }else{
            throw new UsernameNotFoundException("Invalid User Request!");
        }
    }

}
