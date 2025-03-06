package com.example.TimPhongTro.Controller;

import com.example.TimPhongTro.Entity.Post;
import com.example.TimPhongTro.Entity.User;
import com.example.TimPhongTro.Model.Dto.PostDto;
import com.example.TimPhongTro.Model.Mapper.PostMapper;
import com.example.TimPhongTro.Repository.PostRepository;
import com.example.TimPhongTro.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/owner")
public class OwnerController {
    @Autowired
    private UserService userService;

    @Autowired
    private PostRepository postRepository;

    //Quản lý bài đăng của mình
    @PostMapping("")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        User user = userService.getCurrentUser();

        Post post = new Post();
        post.setUser(user);
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setPriceRange(postDto.getPriceRange());
        post.setRoomType(postDto.getRoomType());
        post.setStatus("pending");

        postRepository.save(post);

        return ResponseEntity.ok(PostMapper.toDto(post));
    }
}
