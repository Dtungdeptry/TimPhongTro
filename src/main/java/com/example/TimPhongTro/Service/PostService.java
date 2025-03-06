package com.example.TimPhongTro.Service;

import com.example.TimPhongTro.Model.Dto.PostDto;
import com.example.TimPhongTro.Model.Dto.UserDto;

import java.util.List;

public interface PostService {
    List<PostDto> getAllPosts();


    List<PostDto> getPostByUserId(int userId);

    List<PostDto> getPostsByStatus(String status);

    List<PostDto> getAllPostsPending();

    PostDto createPost(PostDto postDto);

    void deletePostOwner(int userId, int postId);

    List<PostDto> getPostsBySearchCriteria(String priceRange, String roomType, String location, String area);

    void deletePostById(int postId);
}
