package com.example.TimPhongTro.Service;

import com.example.TimPhongTro.Entity.Post;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class PostSpecification {
    public static Specification<Post> searchPosts(String priceRange, String roomType, String location, String area) {
        return (root, query, criteriaBuilder) -> {
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();

            if (priceRange != null) {
                predicates.add(criteriaBuilder.equal(root.get("priceRange").get("name"), priceRange));
            }
            if (roomType != null) {
                predicates.add(criteriaBuilder.equal(root.get("roomType").get("name"), roomType));
            }
            if (location != null) {
                predicates.add(criteriaBuilder.equal(root.get("location").get("name"), location));
            }
            if (area != null) {
                predicates.add(criteriaBuilder.equal(root.get("area").get("name"), area));
            }

            return criteriaBuilder.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };
    }
}


