package com.example.TimPhongTro.Repository;

import com.example.TimPhongTro.Entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {
    RoomType findByTypeName(String typeName);
}
