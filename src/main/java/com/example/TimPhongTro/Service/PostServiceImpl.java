package com.example.TimPhongTro.Service;

import com.example.TimPhongTro.Entity.Post;
import com.example.TimPhongTro.Entity.User;
import com.example.TimPhongTro.Model.Dto.PostDto;
import com.example.TimPhongTro.Model.Mapper.PostMapper;
import com.example.TimPhongTro.Repository.PostRepository;
import com.example.TimPhongTro.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(PostMapper::toDto)
                .toList();
    }

    @Override
    public List<PostDto> getAllPostsPending() {
        List<Post> posts = postRepository.findByStatus("pending");
        return posts.stream()
                .map(PostMapper::toDto)
                .toList();
    }
    // Tạo bài đăng mới
    @Override
    public PostDto createPost(PostDto postDto) {
        // Lấy thông tin User đã đăng nhập từ SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();  // Lấy tên người dùng đã đăng nhập

        User user = userRepository.findByFullNameContaining(username)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postMapper.toEntity(postDto, user);
        post = postRepository.save(post);

        return postMapper.toDto(post);
    }

    @Override
    public List<PostDto> getPostByUserId(int userId) {
        List<Post> posts = postRepository.findByUserId(userId);
        return PostMapper.toDto(posts);
    }

    @Override
    public List<PostDto> getPostsByStatus(String status) {
        List<Post> posts = postRepository.findByStatus(status);

        if (posts.isEmpty()) {
            throw new RuntimeException("Không tìm thấy bài đăng với trạng thái: " + status);
        }

        return PostMapper.toDto(posts);
    }

    @Override
    public void deletePostOwner(int userId, int postId) {
        List<Post> posts = postRepository.findByIdAndUser_Id(postId, userId);
        if (posts.isEmpty()) {
            throw new RuntimeException("Không tìm thấy bài đăng với ID: " + postId + " của người dùng: " + userId);
        }
        postRepository.delete(posts.get(0));
    }

    @Override
    public List<PostDto> getPostsBySearchCriteria(String priceRange, String roomType, String location, String area) {
        List<Post> posts;

        // Tìm kiếm theo tất cả các điều kiện
        if (priceRange != null && roomType != null && location != null && area != null) {
            posts = postRepository.findByPriceRangeAndRoomTypeAndLocationAndArea(priceRange, roomType, location, area);
        }
        // Tìm kiếm theo priceRange, roomType và location
        else if (priceRange != null && roomType != null && location != null) {
            posts = postRepository.findByPriceRangeAndRoomTypeAndLocation(priceRange, roomType, location);
        }
        // Tìm kiếm theo priceRange, roomType và area
        else if (priceRange != null && roomType != null && area != null) {
            posts = postRepository.findByPriceRangeAndRoomTypeAndArea(priceRange, roomType, area);
        }
        // Tìm kiếm theo priceRange, location và area
        else if (priceRange != null && location != null && area != null) {
            posts = postRepository.findByPriceRangeAndAreaAndLocation(priceRange, area, location);
        }
        //Tìm kiếm theo roomType, location và area
        else if (roomType != null && location != null && area != null) {
            posts = postRepository.findByRoomTypeAndLocationAndArea(roomType, location, area);
        }
        // Tìm kiếm theo priceRange và roomType
        else if (priceRange != null && roomType != null) {
            posts = postRepository.findByPriceRangeAndRoomType(priceRange, roomType);
        }
        // Tìm kiếm theo priceRange và location
        else if (priceRange != null && location != null) {
            posts = postRepository.findByPriceRangeAndLocation(priceRange, location);
        }
        // Tìm kiếm theo priceRange và area
        else if (priceRange != null && area != null) {
            posts = postRepository.findByPriceRangeAndArea(priceRange, area);
        }
        // Tìm kiếm theo roomType và location
        else if (roomType != null && location != null) {
            posts = postRepository.findByRoomTypeAndLocation(roomType, location);
        }
        // Tìm kiếm theo roomType và area
        else if (roomType != null && area != null) {
            posts = postRepository.findByRoomTypeAndArea(roomType, area);
        }
        // Tìm kiếm theo location và area
        else if (location != null && area != null) {
            posts = postRepository.findByAreaAndLocation(area, location);
        }
        // Tìm kiếm chỉ theo priceRange
        else if (priceRange != null) {
            posts = postRepository.findByPriceRange(priceRange);
        }
        // Tìm kiếm chỉ theo roomType
        else if (roomType != null) {
            posts = postRepository.findByRoomType(roomType);
        }
        // Tìm kiếm chỉ theo location
        else if (location != null) {
            posts = postRepository.findByLocation(location);
        }
        // Tìm kiếm chỉ theo area
        else if (area != null) {
            posts = postRepository.findByArea(area);
        }
        // Trường hợp không có điều kiện nào, lấy tất cả bài đăng
        else {
            posts = postRepository.findAll();
        }

        return postMapper.toDto(posts); // Chuyển danh sách bài đăng thành DTO
    }



    @Override
    public void deletePostById(int postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài đăng với ID: " + postId));

        postRepository.delete(post);
    }
}
