package com.example.TimPhongTro.Service;

import com.example.TimPhongTro.Entity.Post;
import com.example.TimPhongTro.Entity.User;
import com.example.TimPhongTro.Exception.NotFoundException;
import com.example.TimPhongTro.Model.Dto.PostDto;
import com.example.TimPhongTro.Model.Mapper.PostMapper;
import com.example.TimPhongTro.Repository.PostRepository;
import com.example.TimPhongTro.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        return posts.stream().map(post -> {
            PostDto postDto = new PostDto();
            postDto.setId(post.getId());
            postDto.setTitle(post.getTitle());
            postDto.setContent(post.getContent());
            postDto.setStatus(post.getStatus());
            postDto.setFullName(post.getUser().getFullName());
            postDto.setPhone(post.getUser().getPhone());
            postDto.setCreated_at(post.getCreatedAt());
            return postDto;
        }).collect(Collectors.toList());
    }


    @Override
    public PostDto getPostById(int id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bài đăng với ID " + id + " không tồn tại"));

        // Lấy thông tin người dùng
        User user = post.getUser();
        PostDto postDto = PostMapper.toDto(post);

        // Gán thêm thông tin phone và fullName
        if (user != null) {
            postDto.setFullName(user.getFullName());
            postDto.setPhone(user.getPhone());
        }

        return postDto;
    }


    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = postMapper.toEntity(postDto);

        User user = userRepository.findById(postDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
        post.setUser(user);

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

        // Map Post sang PostDto và gán fullName từ User
        return posts.stream().map(post -> {
            PostDto postDto = PostMapper.toDto(post);
            postDto.setFullName(post.getUser().getFullName());
            postDto.setPhone(post.getUser().getPhone());
            return postDto;
        }).collect(Collectors.toList());
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
    public List<PostDto> getPostsBySearchCriteria(String priceRange, String roomType, String location, String area, String status) {
        Specification<Post> spec = PostSpecification.searchPosts(priceRange, roomType, location, area, status);
        List<Post> posts = postRepository.findAll(spec);
        return postMapper.toDto(posts);
    }

    @Override
    public void deletePostById(int postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài đăng với ID: " + postId));

        postRepository.delete(post);
    }

    @Override
    public PostDto updatePost(int id, PostDto postDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post not found"));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setPriceRange(postDto.getPriceRange());
        post.setRoomType(postDto.getRoomType());
        post.setLocation(postDto.getLocation());
        post.setArea(postDto.getArea());
        post = postRepository.save(post);
        return PostMapper.toDto(post);
    }

    @Override
    public List<PostDto> searchTitle(int userId, String keyword) {
        String processedKeyword = "%" + keyword + "%";
        List<Post> posts = postRepository.findByTitle(userId, processedKeyword);
        return posts.stream()
                .map(PostMapper::toDto)
                .toList();
    }

    @Override
    public PostDto updatePostStatus(int id, String status) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài đăng với ID: " + id));

        post.setStatus(status);
        post = postRepository.save(post);

        return PostMapper.toDto(post);
    }

    @Override
    public List<PostDto> getLatestPosts() {
        List<Post> posts = postRepository.findTop5ByOrderByCreatedAtDesc();
        return PostMapper.toDto(posts);
    }
}
