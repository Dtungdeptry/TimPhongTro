package com.example.TimPhongTro.Controller;

import com.example.TimPhongTro.Exception.NotFoundException;
import com.example.TimPhongTro.Model.Dto.PostDto;
import com.example.TimPhongTro.Model.Dto.UserDto;
import com.example.TimPhongTro.Service.PostService;
import com.example.TimPhongTro.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private PostService postService;

    //Quản lý tài khoản cá nhân (Put)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody UserDto userDto) {
        userDto.setId(id);

        try {
            UserDto updatedUserDto = userService.updateUser(userDto);
            return ResponseEntity.ok(updatedUserDto);
        }
        catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    //Xem thông tin bài đăng
    @GetMapping("")
    public ResponseEntity<List<PostDto>> getAllPostsApproved() {
        List<PostDto> posts = postService.getPostsByStatus("approved");
        return ResponseEntity.ok(posts);
    }

    //Tìm kiếm theo các điều kiện
    @GetMapping("/search")
    public ResponseEntity<List<PostDto>> searchPosts(
            @RequestParam(required = false) String priceRange,
            @RequestParam(required = false) String roomType,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String area) {

        List<PostDto> posts = postService.getPostsBySearchCriteria(priceRange, roomType, location, area);

        return ResponseEntity.ok(posts);
    }
    //Trang thanh toán
}
