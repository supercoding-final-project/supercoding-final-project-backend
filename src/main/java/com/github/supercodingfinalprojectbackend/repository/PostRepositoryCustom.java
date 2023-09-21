package com.github.supercodingfinalprojectbackend.repository;

import com.github.supercodingfinalprojectbackend.entity.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryCustom {
    Page<Posts> filterSearchPosts(String word, List<String> stackCategory, List<String> skillStack, List<String> level, Pageable pageable);
}
