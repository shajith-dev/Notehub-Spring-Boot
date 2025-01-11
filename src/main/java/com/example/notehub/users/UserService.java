package com.example.notehub.users;

import com.example.notehub.aws.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UserService{

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private S3Service s3Service;

    @Value("${aws.bucketName}")
    private String BUCKET_NAME;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUser(Long userId){
        return userDAO.getUser(userId);
    }

    public User getUserByUserName(String userName) {
        return userDAO.getUserByUserName(userName);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public User createUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDAO.createUser(user);
    }

    public String uploadProfilePicture(Long userId,MultipartFile file){
        String fileKey = "profile-pictures/" + userId + "/" + file.getOriginalFilename();
        try {
            s3Service.uploadFile(BUCKET_NAME,fileKey,file);
        }catch (Exception e){
            System.out.println();
        }
        return "";
    }
}
