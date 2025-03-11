package com.example.TimPhongTro.Repository;

import com.example.TimPhongTro.Entity.PriceRange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRangeRepository extends JpaRepository<PriceRange, Integer> {
    PriceRange findByRangeName(String rangeName);
}
