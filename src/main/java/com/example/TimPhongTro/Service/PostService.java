package com.example.TimPhongTro.Service;

import com.example.TimPhongTro.Model.Dto.PostDto;
import java.util.List;

public interface PostService {
    List<PostDto> getAllPosts();

    PostDto getPostById(int id);

    PostDto createPost(PostDto postDto);

    List<PostDto> getPostByUserId(int userId);

    List<PostDto> getPostsByStatus(String status);

    void deletePostOwner(int userId, int postId);

    List<PostDto> getPostsBySearchCriteria(String priceRange, String roomType, String location, String area, String status);

    void deletePostById(int postId);

    PostDto updatePost(int id, PostDto postDto);

    List<PostDto> searchTitle(int userId, String keyword);

    PostDto updatePostStatus(int id, String status);

    List<PostDto> getLatestPosts();
}
