package com.github.supercodingfinalprojectbackend.repository;

import com.github.supercodingfinalprojectbackend.entity.MenteeAbstractAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenteeAbstractAccountRepository extends JpaRepository<MenteeAbstractAccount, Long> {

}
