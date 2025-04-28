package com.example.TimPhongTro.Service;

import com.example.TimPhongTro.Entity.Role;
import com.example.TimPhongTro.Model.Dto.UserDto;
import com.example.TimPhongTro.Model.Mapper.UserMapper;
import com.example.TimPhongTro.Repository.RoleRepository;
import com.example.TimPhongTro.Repository.UserRepository;
import com.example.TimPhongTro.Entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder; // 🔹 Inject PasswordEncoder

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto getUserById(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toDto(user);
    }

    @Override
    public List<UserDto> getUsersByRoleId(int roleId) {
        List<User> users = userRepository.findByRoleId(roleId);

        if (users.isEmpty()) {
            throw new RuntimeException("Không tìm thấy người dùng với roleId: " + roleId);
        }

        return users.stream()
                .map(UserMapper::toDto)
                .toList();
    }


    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> users = userRepository.findByPhoneContainingIgnoreCaseAndRole_Id(keyword, 2);
        return users.stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @Override
    public List<UserDto> searchOwner(String keyword) {
        List<User> users = userRepository.findByPhoneContainingIgnoreCaseAndRole_Id(keyword, 3);
        return users.stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setAddress(userDto.getAddress());
        user.setFullName(userDto.getFullName());

        if (userDto.getRole() != user.getRole().getId()) {
            throw new RuntimeException("Bạn không thể thay đổi vai trò của mình.");
        }

        Role role = roleRepository.findById(userDto.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);

        user = userRepository.save(user);
        return UserMapper.toDto(user);
    }


    @Override
    public void updateUserRole(int id, int roleId) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng" ));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy role có ID: " + roleId));

        user.setRole(role);
        userRepository.save(user);
    }


    @Override
    public void deleteUser(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User updateUserLogin(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setVerified(false);
        userRepository.save(user);
    }


    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return userRepository.findByFullNameContaining(username)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
