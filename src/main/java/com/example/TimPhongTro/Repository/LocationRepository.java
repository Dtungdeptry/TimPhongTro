package com.example.TimPhongTro.Repository;

import com.example.TimPhongTro.Entity.PostLocation;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LocationRepository extends JpaRepository<PostLocation, Integer> {
    PostLocation findByAddress(String address);
}
