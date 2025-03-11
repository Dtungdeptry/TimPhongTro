package com.example.TimPhongTro.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "location")
public class PostLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "address", nullable = false)
    private String address;

    // Getter, Setter, Constructors

    public PostLocation() {
    }

    public PostLocation(int id, String address) {
        this.id = id;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

