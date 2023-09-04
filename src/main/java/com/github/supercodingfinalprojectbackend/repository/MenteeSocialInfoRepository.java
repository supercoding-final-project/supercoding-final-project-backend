package com.github.supercodingfinalprojectbackend.repository;

import com.github.supercodingfinalprojectbackend.entity.MenteeSocialInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenteeSocialInfoRepository extends JpaRepository<MenteeSocialInfo, Long> {

    boolean existsBySocialIdAndSocialPlatform(Long socialId, String socialPlatform);
}
