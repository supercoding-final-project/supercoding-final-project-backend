package com.github.supercodingfinalprojectbackend.repository;

import com.github.supercodingfinalprojectbackend.entity.MenteeSocialInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenteeSocialInfoRepository extends JpaRepository<MenteeSocialInfo, Long> {

    boolean existsBySocialIdAndSocialPlatform(Long socialId, String socialPlatform);

    Optional<MenteeSocialInfo> findBySocialIdAndSocialPlatformAndIsDeletedIsFalse(Long id, String kakao);
}
