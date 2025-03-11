package com.example.TimPhongTro.Controller;

import com.example.TimPhongTro.Entity.Post;
import com.example.TimPhongTro.Entity.User;
import com.example.TimPhongTro.Model.Dto.*;
import com.example.TimPhongTro.Model.Mapper.PostMapper;
import com.example.TimPhongTro.Repository.PostRepository;
import com.example.TimPhongTro.Service.DropdownService;
import com.example.TimPhongTro.Service.PostService;
import com.example.TimPhongTro.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owner")
@CrossOrigin(origins = "http://localhost:3000")
public class OwnerController {
    @Autowired
    private DropdownService dropdownService;

    @Autowired
    private PostService postService;

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
}
