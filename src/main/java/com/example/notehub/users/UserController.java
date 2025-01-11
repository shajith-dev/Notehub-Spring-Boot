package com.example.notehub.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping(path = "/{userId}")
    public User getUser(@PathVariable Long userId){
        return userService.getUser(userId);
    }

    @PostMapping(path = "/{userId}/pfp")
    public String uploadProfilePicture(@PathVariable Long userId,@RequestParam("file")MultipartFile file){
        return userService.uploadProfilePicture(userId,file);
    }

}
