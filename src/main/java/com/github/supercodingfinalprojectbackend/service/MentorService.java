package com.github.supercodingfinalprojectbackend.service;

import com.github.supercodingfinalprojectbackend.dto.MentorCareerDto;
import com.github.supercodingfinalprojectbackend.dto.MentorDto;
import com.github.supercodingfinalprojectbackend.dto.MentorDto.MentorInfoResponse;
import com.github.supercodingfinalprojectbackend.entity.Mentor;
import com.github.supercodingfinalprojectbackend.entity.MentorCareer;
import com.github.supercodingfinalprojectbackend.entity.MentorSkillStack;
import com.github.supercodingfinalprojectbackend.entity.type.SkillStackType;
import com.github.supercodingfinalprojectbackend.exception.ApiException;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.repository.MentorCareerRepository;
import com.github.supercodingfinalprojectbackend.repository.MentorRepository;
import com.github.supercodingfinalprojectbackend.repository.MentorSkillStackRepository;
import com.github.supercodingfinalprojectbackend.repository.SkillStackRepository;
import com.github.supercodingfinalprojectbackend.util.ValidateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.Validate;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode.NOT_FOUND_MENTOR;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class MentorService {

	private final MentorRepository mentorRepository;
	private final MentorSkillStackRepository mentorSkillStackRepository;
	private final SkillStackRepository skillStackRepository;
	private final MentorCareerRepository mentorCareerRepository;

	public Page<MentorDto.MentorInfoResponse> getMentors(
			String keyWord, List<String> skillStacks, Long cursor, Pageable pageable){

//		1차 구현 순수 Entity 조회
//		List<Mentor> mentors = mentorRepository.searchAll(keyWord, skillStacks);

//		2차 구현 DTO 조회
//		List<MentorDto.MentorInfoResponse> mentors = mentorRepository.searchAllFromDto(keyWord, skillStacks);

//		3차 OffsetPagination
//		Page<MentorInfoResponse> mentors = mentorRepository.searchAllFromDtoWithOffsetPagination(keyWord, skillStacks, pageable);

//		4차 cursorPagination
		Page<MentorInfoResponse> mentors = mentorRepository.searchAllFromDtoWithCursorPagination(keyWord, skillStacks, cursor, pageable);

//		1차 응답
//		return mentors.stream().map(MentorDto::fromEntity).collect(Collectors.toList());
		return mentors;
	}

	public MentorDto getMentorDetail(Long mentorId) {
		Mentor mentor = mentorRepository.findById(mentorId)
				.orElseThrow(() -> new ApiException(NOT_FOUND_MENTOR));

		if (!mentor.isValid()) {
			throw new ApiException(NOT_FOUND_MENTOR);
		}
		return MentorDto.from(mentor);
	}

    public MentorDto.ChangeInfoResponse changeMentorInfo(Long userId, MentorDto.ChangeInfoRequest request) {
		Mentor mentor = mentorRepository.findByUserUserIdAndIsDeletedIsFalse(userId).orElseThrow(NOT_FOUND_MENTOR::exception);
		mentor.changeInfo(request);

		List<MentorCareer> oldMentorCareers = mentorCareerRepository.findAllByMentorAndIsDeletedIsFalse(mentor);
		oldMentorCareers.forEach(MentorCareer::softDelete);

		List<MentorCareerDto.Request> careers = request.getCareers();
		if (careers != null && !careers.isEmpty()) {
			List<MentorCareer> mentorCareerList = careers.stream()
					.map(c->MentorCareer.of(mentor, c))
					.map(mentorCareerRepository::save)
					.collect(Collectors.toList());
			mentor.setMentorCareers(mentorCareerList);
		} else {
			mentor.setMentorCareers(null);
		}

		List<MentorSkillStack> oldMentorSkillStacks = mentorSkillStackRepository.findAllByMentorAndIsDeletedIsFalse(mentor);
		oldMentorSkillStacks.forEach(MentorSkillStack::softDelete);

		List<String> skillStacks = request.getSkillStacks();
		if (skillStacks != null && !skillStacks.isEmpty()) {
			List<MentorSkillStack> mentorSkillStackList = skillStacks.stream()
					.map(s->ValidateUtils.requireApply(s, SkillStackType::valueOf, ApiErrorCode.INVALID_SKILL_STACK))
					.map(SkillStackType::getSkillStackCode)
					.map(c->skillStackRepository.findBySkillStackId(c).orElseThrow(ApiErrorCode.INTERNAL_SERVER_ERROR::exception))
					.map(s->MentorSkillStack.of(mentor, s))
					.map(mentorSkillStackRepository::save)
					.collect(Collectors.toList());
			mentor.setMentorSkillStacks(mentorSkillStackList);
		} else {
			mentor.setMentorSkillStacks(null);
		}

		return MentorDto.ChangeInfoResponse.from(mentor);
    }
}
