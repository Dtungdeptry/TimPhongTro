package com.example.TimPhongTro.Service;

import com.example.TimPhongTro.Entity.Role;
import com.example.TimPhongTro.Entity.User;
import com.example.TimPhongTro.Model.Dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto getUserById(int id);

    List<UserDto> getUsersByRoleId(int roleId);

    List<UserDto> searchUser(String keyword);

    List<UserDto> searchOwner(String keyword);

    UserDto updateUser(UserDto userDto);

    void updateUserRole(int id, int roleId);

    void deleteUser(int id);

    User findByUsername(String username);

    User updateUserLogin(User user);

    void registerUser(User user);

    User getCurrentUser();
}
