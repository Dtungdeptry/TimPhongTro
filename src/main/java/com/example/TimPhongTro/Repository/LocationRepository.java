package com.example.TimPhongTro.Repository;

import com.example.TimPhongTro.Entity.Post;
import com.example.TimPhongTro.Entity.PostLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<PostLocation, Integer> {
    PostLocation findByAddress(String address);
}
