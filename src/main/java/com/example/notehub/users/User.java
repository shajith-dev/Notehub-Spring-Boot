package com.example.notehub.users;

import java.time.LocalDateTime;

public class User {
    private Long userId;
    private String username;
    private String emailId;
    private String description;
    private LocalDateTime createdAt;
    private Boolean isDeleted;
    private String url;
    private String password;

    public User() {
    }

    public User(Long userId, String userName, String emailId, String description, LocalDateTime createdAt, Boolean isDeleted, String url, String password) {
        this.userId = userId;
        this.username = userName;
        this.emailId = emailId;
        this.description = description;
        this.createdAt = createdAt;
        this.isDeleted = isDeleted;
        this.url = url;
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + username + '\'' +
                ", emailId='" + emailId + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", isDeleted=" + isDeleted +
                ", url='" + url + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
