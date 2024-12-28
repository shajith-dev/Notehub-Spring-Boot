package com.example.notehub.auth;

import com.example.notehub.users.User;
import com.example.notehub.users.UserDAO;
import com.example.notehub.users.UserInfoDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.getUserByUserName(username);
        if(user != null){
            return new UserInfoDetails(user);
        }else{
            throw new UsernameNotFoundException("User not found:"+ username);
        }
    }

}
