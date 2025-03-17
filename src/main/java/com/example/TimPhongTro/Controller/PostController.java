package com.example.TimPhongTro.Controller;

import com.example.TimPhongTro.Model.Dto.PostDto;
import com.example.TimPhongTro.Repository.PostRepository;
import com.example.TimPhongTro.Service.PostService;
import com.example.TimPhongTro.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

//    @GetMapping("/search")
//    public ResponseEntity<List<PostDto>> searchPosts(
//            @RequestParam(required = false) String priceRange,
//            @RequestParam(required = false) String roomType,
//            @RequestParam(required = false) String location,
//            @RequestParam(required = false) String area) {
//
//        List<PostDto> posts = postService.getPostsBySearchCriteria(priceRange, roomType, location, area);
//
//        return ResponseEntity.ok(posts);
//    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDto>> getPostByUserId(@PathVariable int userId) {
        List<PostDto> posts = postService.getPostByUserId(userId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PostDto>> getPostsByStatus(@PathVariable String status) {
        List<PostDto> posts = postService.getPostsByStatus(status);
        return ResponseEntity.ok(posts);
    }
}
