package com.example.TimPhongTro.Service;

import com.example.TimPhongTro.Entity.User;
import com.example.TimPhongTro.Model.Dto.PostDto;
import com.example.TimPhongTro.Model.Dto.UserDto;

import java.util.List;

public interface PostService {
    List<PostDto> getAllPosts();

    PostDto getPostById(int id);

    PostDto createPost(PostDto postDto);

    List<PostDto> getPostByUserId(int userId);

    List<PostDto> getPostsByStatus(String status);

    void deletePostOwner(int userId, int postId);

    List<PostDto> getPostsBySearchCriteria(String priceRange, String roomType, String location, String area);

    void deletePostById(int postId);

    PostDto updatePost(int id, PostDto postDto);

    List<PostDto> searchTitle(String keyword);
}
