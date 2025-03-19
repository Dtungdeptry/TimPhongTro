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
@RequestMapping("/post")
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable int id) {
        PostDto postDto = postService.getPostById(id);
        return ResponseEntity.ok(postDto);
    }
}
