package com.github.supercodingfinalprojectbackend.repository;

import com.github.supercodingfinalprojectbackend.entity.Mentor;
import java.util.List;

public interface MentorRepositoryCustom {

	List<Mentor> searchAll(String keyWord, List<String> skillStacks);
}
