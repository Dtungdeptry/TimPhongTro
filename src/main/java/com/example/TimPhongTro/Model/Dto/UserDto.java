package com.example.TimPhongTro.Model.Dto;

import jakarta.validation.constraints.NotBlank; // Đối với Jakarta (Spring Boot 3+)
import com.example.TimPhongTro.Entity.User;
import com.example.TimPhongTro.Entity.Role;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class UserDto {
    private int id;
    private String username;

    @NotBlank(message = "Password không được để trống")
    private String password;
    private String email;
    private String phone;
    private String fullName;
    private String address;
    private int role_id;

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    private LocalDateTime created_at;  // Kiểm tra có khai báo không




    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.fullName = user.getFullName();
        this.address = user.getAddress();
        this.role_id = user.getRole().getId();
    }

    public UserDto() {
    }

    @JsonCreator
    public UserDto(
            @JsonProperty("id") int id,
            @JsonProperty("username") String username,
            @JsonProperty("password") String password,
            @JsonProperty("email") String email,
            @JsonProperty("fullName") String fullName,
            @JsonProperty("phone") String phone,
            @JsonProperty("address") String address,
            @JsonProperty("role_id") int role_id) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.role_id = role_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public @NotBlank(message = "Password không được để trống") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password không được để trống") String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRole() {
        return role_id;
    }

    public void setRole(int role_id) {
        this.role_id = role_id;
    }
}
