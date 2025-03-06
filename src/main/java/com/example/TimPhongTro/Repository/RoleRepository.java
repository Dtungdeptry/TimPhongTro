package com.example.TimPhongTro.Repository;

import com.example.TimPhongTro.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(Role.RoleName roleName);
}
