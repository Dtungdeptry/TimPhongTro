package com.example.TimPhongTro.Model.Mapper;

import com.example.TimPhongTro.Entity.Post;
import com.example.TimPhongTro.Entity.Role;
import com.example.TimPhongTro.Entity.User;
import com.example.TimPhongTro.Model.Dto.PostDto;
import com.example.TimPhongTro.Model.Dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostMapper {
    public static PostDto toDto(Post post) {
        if (post == null) {
            return null;
        }
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setUserId(post.getUser().getId());  // Gán userId từ User object
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setStatus(post.getStatus());
        dto.setPriceRange(post.getPriceRange());
        dto.setLocation(post.getLocation());
        dto.setRoomType(post.getRoomType());
        dto.setArea(post.getArea());
        return dto;
    }

    public static Post toEntity(PostDto dto, User user) {
        if (dto == null) {
            return null;
        }

        Post post = new Post();
        post.setId(dto.getId());
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setStatus(dto.getStatus());
        post.setPriceRange(dto.getPriceRange());
        post.setRoomType(dto.getRoomType());
        post.setLocation(dto.getLocation());
        post.setArea(dto.getArea());
        post.setUser(user);
        return post;
    }
    public static List<PostDto> toDto(List<Post> posts) {
        List<PostDto> dtoList = new ArrayList<>();
        for (Post post : posts) {
            dtoList.add(toDto(post));
        }
        return dtoList;
    }
}
