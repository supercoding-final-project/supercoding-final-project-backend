package com.github.supercodingfinalprojectbackend.repository;

import com.github.supercodingfinalprojectbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(Long id);

}
