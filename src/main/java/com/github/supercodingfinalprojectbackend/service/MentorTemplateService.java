package com.github.supercodingfinalprojectbackend.service;


import com.github.supercodingfinalprojectbackend.dto.ScheduleDto;
import com.github.supercodingfinalprojectbackend.entity.Mentor;
import com.github.supercodingfinalprojectbackend.entity.MentorScheduleTemplate;
import com.github.supercodingfinalprojectbackend.entity.type.WeekType;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.repository.MentorRepository;
import com.github.supercodingfinalprojectbackend.repository.MentorScheduleTemplateRepository;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MentorTemplateService {

    private final MentorScheduleTemplateRepository mentorScheduleTemplateRepository;
    private final MentorRepository mentorRepository;
    public ResponseEntity<?> scheduleMentor(ScheduleDto scheduleDto) throws IllegalAccessException {
        Mentor mentor = mentorRepository.findByMentorIdAndIsDeletedIsFalse(scheduleDto.getMentorId()).orElseThrow(ApiErrorCode.NOT_FOUND_USER::exception);

        for (Field field : scheduleDto.getClass().getDeclaredFields()) {
            if(field.getName().equals("mentorId"))continue;
            field.setAccessible(true);

            String week = String.valueOf(WeekType.valueOf(field.getName().toUpperCase()).getName());

            List<MentorScheduleTemplate> exitsMentorTemplate = mentorScheduleTemplateRepository.findByMentorAndScheduleWeek(mentor,week);

            if (exitsMentorTemplate != null){
                mentorScheduleTemplateRepository.deleteAll(exitsMentorTemplate);
            }
            List<Integer> validTime = (List<Integer>) field.get(scheduleDto);
            List<MentorScheduleTemplate> mentorScheduleTemplates = new ArrayList<>();


            for (Integer validTimes : validTime ){
                MentorScheduleTemplate mentorScheduleTemplate = MentorScheduleTemplate.from(week,mentor,validTimes);
                mentorScheduleTemplates.add(mentorScheduleTemplate);
            }
            mentorScheduleTemplateRepository.saveAll(mentorScheduleTemplates);
        }
//        for (int i = 0; i < scheduleDto.getScheduleWeek().size(); i++) {
//            String week = scheduleDto.getScheduleWeek().get(i);
//
//            List<MentorScheduleTemplate> exitsMentorTemplate = mentorScheduleTemplateRepository.findByMentorAndScheduleWeek(mentor,week);
//
//            if (exitsMentorTemplate != null){
//                mentorScheduleTemplateRepository.deleteAll(exitsMentorTemplate);
//            }
//            List<Integer> validTime = scheduleDto.getValidTime().get(i);
//            List<MentorScheduleTemplate> mentorScheduleTemplates = new ArrayList<>();
//
//
//            for (Integer validTimes : validTime ){
//                MentorScheduleTemplate mentorScheduleTemplate = MentorScheduleTemplate.from(week,mentor,validTimes);
//                mentorScheduleTemplates.add(mentorScheduleTemplate);
//            }
//            mentorScheduleTemplateRepository.saveAll(mentorScheduleTemplates);
//        }
        return ResponseUtils.ok("성공적으로 저장되었습니다.",scheduleDto);
    }
}
