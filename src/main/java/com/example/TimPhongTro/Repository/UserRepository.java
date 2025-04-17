package com.example.TimPhongTro.Repository;

import com.example.TimPhongTro.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByFullNameContaining(String fullName);

    List<User> findByRoleId(int roleId);

    @Query("SELECT u FROM User u WHERE u.username = :username")
    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE LOWER(u.phone) LIKE LOWER(CONCAT('%', :phoneKeyword, '%')) AND u.role.id = :roleId")
    List<User> findByPhoneContainingIgnoreCaseAndRole_Id(@Param("phoneKeyword") String phoneKeyword, @Param("roleId") int roleId);
}
