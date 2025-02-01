package com.example.notehub.auth;

public class AuthResponse {
    private Long userId;
    private String userName;
    private String token;

    public AuthResponse(Long userId, String userName, String token) {
        this.userId = userId;
        this.userName = userName;
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
