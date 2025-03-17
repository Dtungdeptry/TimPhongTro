package com.example.TimPhongTro.Controller;

import com.example.TimPhongTro.Entity.*;
import com.example.TimPhongTro.Model.Dto.*;
import com.example.TimPhongTro.Repository.PostRepository;
import com.example.TimPhongTro.Service.DropdownService;
import com.example.TimPhongTro.Service.PostService;
import com.example.TimPhongTro.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/owner")
@CrossOrigin(origins = "http://localhost:3000")
public class OwnerController {
    @Autowired
    private DropdownService dropdownService;

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    //Quản lý bài đăng của mình (thêm sửa xóa, xem chi tiết bài đăng, tìm kiếm)
    @GetMapping("/post/{userId}")
    public ResponseEntity<?> getPostsByUserId(@PathVariable int userId) {
        List<PostDto> posts = postService.getPostByUserId(userId);
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/post/{userId}")
    public ResponseEntity<?> createPost(@PathVariable int userId, @RequestBody PostDto postDto) {
        postDto.setStatus("pending");  // Thiết lập trạng thái mặc định là "pending"
        postDto.setUserId(userId);
        PostDto newPostDto = postService.createPost(postDto);
        return ResponseEntity.ok(newPostDto);
    }

    @PutMapping("/post/{userId}/{id}")
    public ResponseEntity<?> updatePost(@PathVariable int userId, @PathVariable int id, @RequestBody PostDto postDto) {
        postDto.setUserId(userId);
        PostDto updatedPost = postService.updatePost(id, postDto);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/post/{userId}/{id}")
    public ResponseEntity<?> deletePost(@PathVariable int userId, @PathVariable int id) {
        postService.deletePostById(id);
        return ResponseEntity.ok("Bài đăng đã được xóa thành công");
    }

    @GetMapping("/post/{userId}/search") //search fullname
    public ResponseEntity<?> searchTitle(@RequestParam String keyword) {
        List<PostDto> posts = postService.searchTitle(keyword);
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
    @GetMapping("/post/{userId}/list-page")
    public ResponseEntity<Page<Post>> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postPage = postRepository.findAll(pageable);
        return ResponseEntity.ok(postPage);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getOwnerById(@PathVariable int userId) {
        UserDto userDto = userService.getUserById(userId);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateOwner(@RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateUser(userDto);
        return ResponseEntity.ok(updatedUser);
    }
}
