package com.example.TimPhongTro.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    //ROLE_USER, ROLE_ADMIN, ROLE_OWNER
    public enum RoleName {
        ROLE_USER, ROLE_ADMIN, ROLE_OWNER
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoleName getRole_name() {
        return roleName;
    }

    public void setRole_name(RoleName role_name) {
        this.roleName = roleName;
    }
}
