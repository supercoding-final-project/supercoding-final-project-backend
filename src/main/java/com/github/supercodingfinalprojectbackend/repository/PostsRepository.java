package com.github.supercodingfinalprojectbackend.repository;

import com.github.supercodingfinalprojectbackend.entity.Mentor;
import com.github.supercodingfinalprojectbackend.entity.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostsRepository extends JpaRepository<Posts,Long> {
    Optional<Posts> findByPostIdAndIsDeletedFalse(Long postId);

    Page<Posts> findAllByMentorAndIsDeletedFalse(Mentor mentor, Pageable pageable);
    List<Posts> findAllByMentorAndIsDeletedFalse(Mentor mentor);
}
