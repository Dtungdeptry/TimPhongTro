package com.example.TimPhongTro.Repository;

import com.example.TimPhongTro.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer>, JpaSpecificationExecutor<Post> {
    List<Post> findByUserId(int userId); // Dành cho admin
    List<Post> findByIdAndUser_Id(int id, int userId);
    List<Post> findByStatus(String status);
    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword%")
    List<Post> findByTitle(@Param("keyword") String keyword);
}
