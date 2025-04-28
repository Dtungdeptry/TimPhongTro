package com.example.TimPhongTro.Controller;

import com.example.TimPhongTro.Model.Dto.*;
import com.example.TimPhongTro.Service.DropdownService;
import com.example.TimPhongTro.Service.PostService;
import com.example.TimPhongTro.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private DropdownService dropdownService;

    @Autowired
    private PostService postService;

    // Quản lý tài khoản cá nhân (Get, Put)
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable int userId) {
        try {
            UserDto userDto = userService.getUserById(userId);
            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra khi lấy thông tin người dùng: " + e.getMessage());
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDto) {
        try {
            UserDto updatedUser = userService.updateUser(userDto);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra khi cập nhật thông tin người dùng: " + e.getMessage());
        }
    }

    // Xem thông tin bài đăng
    @GetMapping("/post/status/approved")
    public ResponseEntity<List<PostDto>> getApprovedPosts() {
        try {
            List<PostDto> posts = postService.getPostsByStatus("approved");
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    // Tìm kiếm theo các điều kiện
    @GetMapping("/search")
    public ResponseEntity<List<PostDto>> searchPosts(
            @RequestParam(required = false) String priceRange,
            @RequestParam(required = false) String roomType,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String area,
            @RequestParam(required = false, defaultValue = "approved") String status) {
        try {
            List<PostDto> posts = postService.getPostsBySearchCriteria(priceRange, roomType, location, area, status);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    // Lấy danh sách các mức giá
    @GetMapping("/post/price-ranges")
    public ResponseEntity<List<PriceRangeDto>> getPriceRanges() {
        try {
            List<PriceRangeDto> priceRanges = dropdownService.getAllPriceRanges();
            return ResponseEntity.ok(priceRanges);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    // Lấy danh sách các loại phòng
    @GetMapping("/post/room-types")
    public ResponseEntity<List<RoomTypeDto>> getRoomTypes() {
        try {
            List<RoomTypeDto> roomTypes = dropdownService.getAllRoomTypes();
            return ResponseEntity.ok(roomTypes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    // Lấy danh sách các vị trí
    @GetMapping("/post/locations")
    public ResponseEntity<List<PostLocationDto>> getLocations() {
        try {
            List<PostLocationDto> locations = dropdownService.getAllLocations();
            return ResponseEntity.ok(locations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    // Lấy danh sách các khu vực
    @GetMapping("/post/areas")
    public ResponseEntity<List<AreaDto>> getAreas() {
        try {
            List<AreaDto> areas = dropdownService.getAllAreas();
            return ResponseEntity.ok(areas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    // Lấy danh sách các bài đăng mới nhất
    @GetMapping("/post/latest")
    public ResponseEntity<List<PostDto>> getLatestPosts() {
        try {
            List<PostDto> posts = postService.getLatestPosts();
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    //Trang thanh toán

}
