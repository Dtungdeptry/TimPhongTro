package com.example.TimPhongTro.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "price_range")
public class PriceRange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "range_name", nullable = false)
    private String rangeName;
    // Getter, Setter, Constructors
    public PriceRange() {
    }

    public PriceRange(int id, String rangeName) {
        this.id = id;
        this.rangeName = rangeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRangeName() {
        return rangeName;
    }

    public void setRangeName(String rangeName) {
        this.rangeName = rangeName;
    }


}

