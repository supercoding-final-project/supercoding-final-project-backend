package com.github.supercodingfinalprojectbackend.service;

import com.github.supercodingfinalprojectbackend.dto.MentorDto;
import com.github.supercodingfinalprojectbackend.entity.Mentor;
import com.github.supercodingfinalprojectbackend.repository.MentorRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MentorService {

	private final MentorRepository mentorRepository;


	public List<MentorDto> getMentors(String keyWord, List<String> skillStacks){

		log.info("mentorName : {}", keyWord);
		List<Mentor> mentors = mentorRepository.searchAll(keyWord, skillStacks);
		log.info("mentors : {}", mentors);
		return mentors.stream().map(MentorDto::fromEntity).collect(Collectors.toList());
	}
}
