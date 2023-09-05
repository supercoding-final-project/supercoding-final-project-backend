package com.github.supercodingfinalprojectbackend.repository;

import com.github.supercodingfinalprojectbackend.entity.UserSocialInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSocialInfoRepository extends JpaRepository<UserSocialInfo, Long> {

    Optional<UserSocialInfo> findBySocialIdAndSocialPlatformNameAndIsDeletedIsFalse(Long socialId, String socialPlatformName);
}
