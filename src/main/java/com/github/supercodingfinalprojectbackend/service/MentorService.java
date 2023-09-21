package com.github.supercodingfinalprojectbackend.service;

import com.github.supercodingfinalprojectbackend.dto.BatchDto;
import com.github.supercodingfinalprojectbackend.dto.MentorCareerDto;
import com.github.supercodingfinalprojectbackend.dto.MentorDto;
import com.github.supercodingfinalprojectbackend.dto.MentorDto.MentorInfoResponse;
import com.github.supercodingfinalprojectbackend.entity.Mentor;
import com.github.supercodingfinalprojectbackend.entity.MentorCareer;
import com.github.supercodingfinalprojectbackend.entity.MentorSkillStack;
import com.github.supercodingfinalprojectbackend.exception.ApiException;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.repository.MentorCareerRepository;
import com.github.supercodingfinalprojectbackend.repository.MentorRepository;
import com.github.supercodingfinalprojectbackend.repository.MentorSkillStackRepository;
import com.github.supercodingfinalprojectbackend.repository.SkillStackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
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
			String keyWord, List<String> skillStacks, List<String> duties, Long cursor, Pageable pageable){

//		Page<MentorInfoResponse> mentors = mentorRepository.searchAllFromDtoWithCursorPagination(keyWord, skillStacks, duties, cursor, pageable);
		Page<MentorInfoResponse> mentors = mentorRepository.searchAllFromDtoWithOffsetPagination(keyWord, skillStacks, duties, pageable);
		postKeywordForBatchProcessing(keyWord);

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

		mentorCareerRepository.deleteAllByMentor(mentor);

		List<MentorCareerDto.Request> careers = request.getCareers();
		if (careers != null && !careers.isEmpty()) {
			List<MentorCareer> mentorCareerList = mentorCareerRepository.saveAll(
					careers.stream()
							.map(c->MentorCareer.of(mentor, c))
							.collect(Collectors.toList())
			);
			mentor.setMentorCareers(mentorCareerList);
		} else {
			mentor.setMentorCareers(null);
		}

		mentorSkillStackRepository.deleteAllByMentor(mentor);

		List<String> skillStackNames = request.getSkillStacks();
		if (skillStackNames != null && !skillStackNames.isEmpty()) {
			List<MentorSkillStack> mentorSkillStackList = mentorSkillStackRepository.saveAll(
					skillStackNames.stream()
							.map(skillStackName->skillStackRepository.findBySkillStackName(skillStackName).orElseThrow(ApiErrorCode.INVALID_SKILL_STACK::exception))
							.map(skillStack->MentorSkillStack.of(mentor, skillStack))
							.collect(Collectors.toList())
			);
			mentor.setMentorSkillStacks(mentorSkillStackList);
		} else {
			mentor.setMentorSkillStacks(null);
		}

		return MentorDto.ChangeInfoResponse.from(mentor);
    }

    public MentorDto.InfoResponse getMentorInfo(Long userId) {
		Mentor mentor = mentorRepository.findByUserUserIdAndIsDeletedIsFalse(userId).orElseThrow(NOT_FOUND_MENTOR::exception);
		List<MentorCareer> mentorCareers = mentorCareerRepository.findAllByMentorAndIsDeletedIsFalse(mentor);
		List<MentorSkillStack> mentorSkillStacks = mentorSkillStackRepository.findAllByMentorAndIsDeletedIsFalse(mentor);

		return MentorDto.InfoResponse.of(mentor, mentorCareers, mentorSkillStacks);
    }

	public void postKeywordForBatchProcessing(String keyword) {
		if (Objects.equals(keyword, "")) {
			return;
		}
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		BatchDto data = new BatchDto(keyword);

		HttpEntity<BatchDto> requestEntity = new HttpEntity<>(data, headers);

		restTemplate.postForEntity("http://15.164.53.189:8081/v1/api/batch", requestEntity, String.class);
	}
}
