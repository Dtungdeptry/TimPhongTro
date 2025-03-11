package com.example.TimPhongTro.Model.Dto;

public class AuthResponse {
    private String token;
    private int userId;
    private int roleId;

    public AuthResponse(String token, int userId, int roleId) {
        this.token = token;
        this.userId = userId;
        this.roleId = roleId;
    }

    public String getToken() {
        return token;
    }

    public int getUserId() {
        return userId;
    }

    public int getRoleId() {
        return roleId;
    }
}

