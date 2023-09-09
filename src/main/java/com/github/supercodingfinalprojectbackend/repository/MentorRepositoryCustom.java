package com.github.supercodingfinalprojectbackend.repository;

import com.github.supercodingfinalprojectbackend.dto.MentorDto.MentorInfoResponse;
import com.github.supercodingfinalprojectbackend.entity.Mentor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MentorRepositoryCustom {

	List<Mentor> searchAll(String keyword, List<String> skillStacks);

	List<MentorInfoResponse> searchAllFromDto(String keyword, List<String> skillStacks);

	Page<MentorInfoResponse> searchAllFromDtoWithOffsetPagination(String keyword,
			List<String> skillStacks, Pageable pageable);

	Page<MentorInfoResponse> searchAllFromDtoWithCursorPagination(String keyword,
			List<String> skillStacks, Long cursor, Pageable pageable);
}
