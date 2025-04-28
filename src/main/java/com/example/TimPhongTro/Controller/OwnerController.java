package com.example.TimPhongTro.Controller;

import com.example.TimPhongTro.Entity.*;
import com.example.TimPhongTro.Exception.NotFoundException;
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

    // Quản lý bài đăng của mình (thêm sửa xóa, xem chi tiết bài đăng, tìm kiếm)

    @GetMapping("/post/{userId}")
    public ResponseEntity<?> getPostsByUserId(@PathVariable int userId) {
        try {
            List<PostDto> posts = postService.getPostByUserId(userId);
            if (posts.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không có bài đăng nào.");
            }
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã có lỗi xảy ra: " + e.getMessage());
        }
    }

    @PostMapping("/post/{userId}")
    public ResponseEntity<?> createPost(@PathVariable int userId, @RequestBody PostDto postDto) {
        try {
            postDto.setStatus("pending");
            postDto.setUserId(userId);
            PostDto newPostDto = postService.createPost(postDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(newPostDto);  // Đổi thành CREATED (201)
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã có lỗi xảy ra khi tạo bài đăng: " + e.getMessage());
        }
    }

    @PutMapping("/post/{userId}/{id}")
    public ResponseEntity<?> updatePost(@PathVariable int userId, @PathVariable int id, @RequestBody PostDto postDto) {
        try {
            postDto.setUserId(userId);
            PostDto updatedPost = postService.updatePost(id, postDto);
            if (updatedPost == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bài đăng không tồn tại.");
            }
            return ResponseEntity.ok(updatedPost);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã có lỗi xảy ra khi cập nhật bài đăng: " + e.getMessage());
        }
    }

    @DeleteMapping("/post/{userId}/{id}")
    public ResponseEntity<?> deletePost(@PathVariable int userId, @PathVariable int id) {
        try {
            postService.deletePostById(id);
            return ResponseEntity.ok("Bài đăng đã được xóa thành công");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bài đăng không tồn tại.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã có lỗi xảy ra khi xóa bài đăng.");
        }
    }

    @GetMapping("/post/{userId}/search")
    public ResponseEntity<?> searchTitle(@PathVariable int userId, @RequestParam String keyword) {
        try {
            List<PostDto> posts = postService.searchTitle(userId, keyword);
            if (posts.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không có bài đăng nào khớp với từ khóa.");
            }
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã có lỗi xảy ra khi tìm kiếm bài đăng: " + e.getMessage());
        }
    }

    @GetMapping("/post/price-ranges")
    public ResponseEntity<?> getPriceRanges() {
        try {
            List<PriceRangeDto> priceRanges = dropdownService.getAllPriceRanges();
            return ResponseEntity.ok(priceRanges);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi khi lấy danh sách khoảng giá.");
        }
    }

    @GetMapping("/post/room-types")
    public ResponseEntity<?> getRoomTypes() {
        try {
            List<RoomTypeDto> roomTypes = dropdownService.getAllRoomTypes();
            return ResponseEntity.ok(roomTypes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi khi lấy danh sách loại phòng.");
        }
    }

    @GetMapping("/post/locations")
    public ResponseEntity<?> getLocations() {
        try {
            List<PostLocationDto> locations = dropdownService.getAllLocations();
            return ResponseEntity.ok(locations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi khi lấy danh sách địa điểm.");
        }
    }

    @GetMapping("/post/areas")
    public ResponseEntity<?> getAreas() {
        try {
            List<AreaDto> areas = dropdownService.getAllAreas();
            return ResponseEntity.ok(areas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi khi lấy danh sách khu vực.");
        }
    }

    @GetMapping("/post/{userId}/list-page")
    public ResponseEntity<?> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Post> postPage = postRepository.findAll(pageable);
            return ResponseEntity.ok(postPage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi khi lấy bài đăng.");
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getOwnerById(@PathVariable int userId) {
        try {
            UserDto userDto = userService.getUserById(userId);
            if (userDto == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy người dùng.");
            }
            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi khi lấy thông tin người dùng.");
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateOwner(@RequestBody UserDto userDto) {
        try {
            UserDto updatedUser = userService.updateUser(userDto);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi khi cập nhật thông tin người dùng.");
        }
    }

}
