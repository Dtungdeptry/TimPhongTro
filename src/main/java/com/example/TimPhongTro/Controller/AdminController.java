package com.example.TimPhongTro.Controller;

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
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    //Quản lý tất cả bài đăng
    @GetMapping("/post")
    public ResponseEntity<?> getAllPosts() {
        try {
            List<PostDto> posts = postService.getAllPosts();
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Đã xảy ra lỗi khi lấy danh sách bài đăng.");
        }
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<?> getPostById(@PathVariable int id) {
        try {
            PostDto postDto = postService.getPostById(id);
            return ResponseEntity.ok(postDto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Không thể lấy bài đăng với id = " + id);
        }
    }

    // Danh sách bài đăng đang trong trạng thái pending (get)
    @GetMapping("/post/status/pending")
    public ResponseEntity<?> getPendingPosts() {
        try {
            List<PostDto> posts = postService.getPostsByStatus("pending");
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Không thể lấy danh sách bài đăng đang pending.");
        }
    }

    // Cập nhật trạng thái bài đăng (put)
    @PutMapping("/post/{id}")
    public ResponseEntity<?> updatePostStatus(
            @PathVariable int id,
            @RequestParam String status) {
        try {
            PostDto updatedPostDto = postService.updatePostStatus(id, status);
            return ResponseEntity.ok(updatedPostDto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Cập nhật trạng thái thất bại cho bài đăng id = " + id);
        }
    }

    // Danh sách bài đăng đã được approved (get)
    @GetMapping("/post/status/approved")
    public ResponseEntity<?> getApprovedPosts() {
        try {
            List<PostDto> posts = postService.getPostsByStatus("approved");
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Không thể lấy danh sách bài đăng đã được duyệt.");
        }
    }

    // Xóa bài đăng đã được approved (delete)
    @DeleteMapping("/post/status/approved/{id}")
    public ResponseEntity<?> deleteApprovedPost(@PathVariable int id) {
        try {
            postService.deletePostById(id);
            return ResponseEntity.ok("Bài đăng đã được xóa thành công.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Xóa bài đăng thất bại với id = " + id);
        }
    }

    // Danh sách bài đăng bị rejected (get)
    @GetMapping("/post/status/rejected")
    public ResponseEntity<?> getRejectedPosts() {
        try {
            List<PostDto> posts = postService.getPostsByStatus("rejected");
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Không thể lấy danh sách bài đăng bị từ chối.");
        }
    }

    // Lấy danh sách Owner
    @GetMapping("/owners")
    public ResponseEntity<?> getOwnersByRoleId() {
        try {
            List<UserDto> users = userService.getUsersByRoleId(3);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Không thể lấy danh sách chủ nhà.");
        }
    }

    // Lấy Owner theo ID
    @GetMapping("/owners/{id}")
    public ResponseEntity<?> getOwnerById(@PathVariable int id) {
        try {
            UserDto userDto = userService.getUserById(id);
            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Không thể lấy thông tin chủ nhà với id = " + id);
        }
    }

    // Tìm kiếm Owner theo từ khóa (fullname)
    @GetMapping("/owners/search")
    public ResponseEntity<?> searchOwner(@RequestParam String keyword) {
        try {
            List<UserDto> users = userService.searchOwner(keyword);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Không thể tìm kiếm chủ nhà với từ khóa: " + keyword);
        }
    }

    // Xóa Owner theo ID
    @DeleteMapping("/owners/{id}")
    public ResponseEntity<?> deleteOwner(@PathVariable int id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("Chủ nhà đã được xóa thành công.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Xóa chủ nhà thất bại với id = " + id);
        }
    }

    // Lấy danh sách bài đăng theo ID Owner
    @GetMapping("/posts/{userId}")
    public ResponseEntity<?> getPostsByUserId(@PathVariable int userId) {
        try {
            List<PostDto> posts = postService.getPostByUserId(userId);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Không thể lấy danh sách bài đăng của chủ nhà.");
        }
    }

    // Xóa bài đăng của Owner
    @DeleteMapping("/posts/{userId}/{id}")
    public ResponseEntity<?> deletePostOwner(@PathVariable int userId, @PathVariable int id) {
        try {
            postService.deletePostOwner(userId, id);
            return ResponseEntity.ok("Xóa bài đăng thành công.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Xóa bài đăng thất bại.");
        }
    }

    // Lấy danh sách User
    @GetMapping("/users")
    public ResponseEntity<?> getUsersByRoleId() {
        try {
            List<UserDto> users = userService.getUsersByRoleId(2);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Không thể lấy danh sách người dùng.");
        }
    }

    // Lấy User theo ID
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        try {
            UserDto userDto = userService.getUserById(id);
            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Không thể lấy thông tin người dùng với id = " + id);
        }
    }

    // Tìm kiếm User theo từ khóa
    @GetMapping("/users/search")
    public ResponseEntity<?> searchUser(@RequestParam String keyword) {
        try {
            List<UserDto> users = userService.searchUser(keyword);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Không thể tìm kiếm người dùng với từ khóa: " + keyword);
        }
    }

    // Cập nhật quyền từ User thành Owner
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateOwnerRoleFromUser(@PathVariable int id, @RequestBody UserDto userDto) {
        try {
            int newRoleId = userDto.getRole();
            userService.updateUserRole(id, newRoleId);
            return ResponseEntity.ok("Cập nhật quyền thành công.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Cập nhật quyền thất bại.");
        }
    }

    // Xóa User
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("Xóa người dùng thành công.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Xóa người dùng thất bại.");
        }
    }

}

