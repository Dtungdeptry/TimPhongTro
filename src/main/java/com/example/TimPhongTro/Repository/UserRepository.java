package com.example.TimPhongTro.Repository;

import com.example.TimPhongTro.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByFullNameContaining(String fullName);

    List<User> findByRoleId(int roleId);
    User findByUsername(String username);
    List<User> findByFullNameContainingIgnoreCaseAndRole_Id(String keyword, int roleId);

}
