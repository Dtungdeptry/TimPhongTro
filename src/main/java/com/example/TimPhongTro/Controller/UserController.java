package com.example.TimPhongTro.Controller;

import com.example.TimPhongTro.Model.Dto.*;
import com.example.TimPhongTro.Service.DropdownService;
import com.example.TimPhongTro.Service.PostService;
import com.example.TimPhongTro.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //Quản lý tài khoản cá nhân (Get, Put)
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable int userId) {
        UserDto userDto = userService.getUserById(userId);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateUser(userDto);
        return ResponseEntity.ok(updatedUser);
    }

    //Xem thông tin bài đăng
    @GetMapping("/post/status/approved")
    public ResponseEntity<List<PostDto>> getApprovedPosts() {
        List<PostDto> posts = postService.getPostsByStatus("approved");
        return ResponseEntity.ok(posts);
    }

    //Tìm kiếm theo các điều kiện
    @GetMapping("/search")
    public ResponseEntity<List<PostDto>> searchPosts(
            @RequestParam(required = false) String priceRange,
            @RequestParam(required = false) String roomType,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String area,
            @RequestParam(required = false, defaultValue = "approved") String status) {

        List<PostDto> posts = postService.getPostsBySearchCriteria(priceRange, roomType, location, area, status);

        return ResponseEntity.ok(posts);
    }

    @GetMapping("/post/price-ranges")
    public List<PriceRangeDto> getPriceRanges() {
        return dropdownService.getAllPriceRanges();
    }

    @GetMapping("/post/room-types")
    public List<RoomTypeDto> getRoomTypes() {
        return dropdownService.getAllRoomTypes();
    }

    @GetMapping("/post/locations")
    public List<PostLocationDto> getLocations() {
        return dropdownService.getAllLocations();
    }

    @GetMapping("/post/areas")
    public List<AreaDto> getAreas() {
        return dropdownService.getAllAreas();
    }
    //Trang thanh toán
}
