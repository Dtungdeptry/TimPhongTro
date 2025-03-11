package com.example.TimPhongTro.Controller;

import com.example.TimPhongTro.Config.JwtUtil;
import com.example.TimPhongTro.Entity.Post;
import com.example.TimPhongTro.Entity.User;
import com.example.TimPhongTro.Exception.NotFoundException;
import com.example.TimPhongTro.Model.Dto.PostDto;
import com.example.TimPhongTro.Model.Dto.UserDto;
import com.example.TimPhongTro.Repository.PostRepository;
import com.example.TimPhongTro.Service.PostService;
import com.example.TimPhongTro.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private JwtUtil jwtUtil;

    //Quản lý tất cả bài đăng
    @GetMapping("/post")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("post/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable int id) {
        PostDto postDto = postService.getPostById(id);
        return ResponseEntity.ok(postDto);
    }

    //Danh sách bài đăng đang trong trạng thái pending (get, put)
    @GetMapping("/post/status/pending")
    public ResponseEntity<List<PostDto>> getPendingPosts() {
        List<PostDto> posts = postService.getPostsByStatus("pending");
        return ResponseEntity.ok(posts);
    }


    //Danh sách bài đăng đã được accepted (get, delete) (Xong)
    @GetMapping("/post/status/approved")
    public ResponseEntity<List<PostDto>> getApprovedPosts() {
        List<PostDto> posts = postService.getPostsByStatus("approved");
        return ResponseEntity.ok(posts);
    }

    @DeleteMapping("/post/status/approved/{id}")
    public ResponseEntity<?> deleteApprovedPost(@PathVariable int id) {
            postService.deletePostById(id);
            return ResponseEntity.ok("Bài đăng đã được xóa thành công");
    }

    //Danh sách bài đăng bị rejected (get)
    @GetMapping("/post/status/rejected")
    public ResponseEntity<List<PostDto>> getRejectedPosts() {
        List<PostDto> posts = postService.getPostsByStatus("rejected");
        return ResponseEntity.ok(posts);
    }

    //Danh sách Owner (get, delete, search)
    @GetMapping("/owners")
    public ResponseEntity<?> getOwnersByRoleId() {
            List<UserDto> users = userService.getUsersByRoleId(3);
            return ResponseEntity.ok(users);
    }

    @GetMapping("/owners/{id}")
    public ResponseEntity<?> getOwnerById(@PathVariable int id) {
        UserDto userDto = userService.getUserById(id);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/owners/search") //search fullname
    public ResponseEntity<?> searchOwner(@RequestParam String keyword) {
        List<UserDto> users = userService.searchOwner(keyword);
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/owners/{id}")
    public ResponseEntity<?> deleteOwner(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Owner đã được xóa thành công");
    }

    //Danh sách bài đăng theo Id của Owner (get, delete)
    @GetMapping("/posts/{userId}")
    public ResponseEntity<?> getPostsByUserId(@PathVariable int userId) {
        List<PostDto> posts = postService.getPostByUserId(userId);
        return ResponseEntity.ok(posts);
    }

    @DeleteMapping("/posts/{userId}/{id}")
    public ResponseEntity<?> deletePostOwner(@PathVariable int userId, @PathVariable int id) {
        postService.deletePostOwner(userId, id);
        return ResponseEntity.ok("Xóa bài đăng thành công");
    }

    //Danh sách User (get, delete, put, search)
    @GetMapping("/users")
    public ResponseEntity<?> getUsersByRoleId() {
        List<UserDto> users = userService.getUsersByRoleId(2);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        UserDto userDto = userService.getUserById(id);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/users/search")
    public ResponseEntity<?> searchUser(@RequestParam String keyword) {
        List<UserDto> users = userService.searchUser(keyword);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateOwnerRoleFromUser(@PathVariable int id, @RequestBody UserDto userDto) {
        int newRoleId = userDto.getRole();
        userService.updateUserRole(id, newRoleId);
        return ResponseEntity.ok("Cập nhật quyền thành công");
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Xóa người dùng thành công");
    }
}
