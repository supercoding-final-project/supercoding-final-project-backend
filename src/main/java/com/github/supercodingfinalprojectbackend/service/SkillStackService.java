package com.github.supercodingfinalprojectbackend.service;

import com.github.supercodingfinalprojectbackend.dto.SkillStackDto;
import com.github.supercodingfinalprojectbackend.repository.SkillStackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class SkillStackService {

    private final SkillStackRepository skillStackRepository;

    public List<SkillStackDto> getTop10SkillStacks() {
        return skillStackRepository
                .findTop10ByOrderBySkillStackSearchCountDesc(PageRequest.of(0, 10))
                .stream().map(SkillStackDto::from)
                .collect(Collectors.toList());
    }
}
