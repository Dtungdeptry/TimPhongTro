package com.example.TimPhongTro.Model.Mapper;

import com.example.TimPhongTro.Model.Dto.UserDto;
import com.example.TimPhongTro.Entity.User;
import com.example.TimPhongTro.Entity.Role;

public class UserMapper {
    public static UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setFullName(user.getFullName());
        dto.setAddress(user.getAddress());
        // Lấy role_id từ Role object
        dto.setRole(user.getRole() != null ? user.getRole().getId() : null); // ✅ Chỉ lưu role_id
        return dto;
    }

    // Chuyển từ DTO sang Entity
    public static User toEntity(UserDto dto, Role role) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setFullName(dto.getFullName());
        user.setAddress(dto.getAddress());
        user.setRole(role); // ✅ Gán Role object
        return user;
    }
}
