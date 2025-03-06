package com.example.TimPhongTro.Repository;

import com.example.TimPhongTro.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUserId(int userId); // Dành cho admin
    List<Post> findByIdAndUser_Id(int id, int userId);
    List<Post> findByStatus(String status);
    List<Post> findByPriceRange(String priceRange); // Dành cho user
    List<Post> findByRoomType(String roomType);
    List<Post> findByLocation(String Location);
    List<Post> findByArea(String area);

    List<Post> findByPriceRangeAndRoomType(String priceRange, String roomType);
    List<Post> findByPriceRangeAndLocation(String priceRange, String location);
    List<Post> findByPriceRangeAndArea(String priceRange, String area);
    List<Post> findByRoomTypeAndLocation(String roomType, String location);
    List<Post> findByRoomTypeAndArea(String roomType, String area);
    List<Post> findByAreaAndLocation(String area, String location);
    List<Post> findByPriceRangeAndRoomTypeAndLocation(String priceRange, String roomType, String location);
    List<Post> findByPriceRangeAndRoomTypeAndArea(String priceRange, String roomType, String area);
    List<Post> findByPriceRangeAndAreaAndLocation(String priceRange, String area, String location);
    List<Post> findByRoomTypeAndLocationAndArea(String roomType, String location,String area);
    List<Post> findByPriceRangeAndRoomTypeAndLocationAndArea(String priceRange, String roomType, String location, String area);
}
