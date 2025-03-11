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
    public PostDto getPostById(int id) {
        return postRepository.findById(id)
                .map(PostMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Bài đăng với ID " + id + " không tồn tại"));
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = postMapper.toEntity(postDto);

        // Lấy đối tượng User từ UserRepository và gán vào Post
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
        Specification<Post> spec = PostSpecification.searchPosts(priceRange, roomType, location, area);
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
    public List<PostDto> searchTitle(String keyword) {
        List<Post> posts = postRepository.findByTitle(keyword);
        return posts.stream()
                .map(PostMapper::toDto)
                .toList();
    }
}
