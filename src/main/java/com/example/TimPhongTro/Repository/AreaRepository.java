package com.example.TimPhongTro.Repository;

import com.example.TimPhongTro.Entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaRepository extends JpaRepository<Area, Integer> {
    Area findBySize(String size);
}
