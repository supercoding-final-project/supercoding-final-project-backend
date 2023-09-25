//package com.github.supercodingfinalprojectbackend.service;
//
//import com.github.supercodingfinalprojectbackend.entity.*;
//import com.github.supercodingfinalprojectbackend.repository.*;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Random;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//@SpringBootTest
//public class MentorDummyService {
//
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private MentorRepository mentorRepository;
//    @Autowired
//    private UserAbstractAccountRepository userAbstractAccountRepository;
//    @Autowired
//    private MentorCareerRepository mentorCareerRepository;
//    @Autowired
//    private MentorSkillStackRepository mentorSkillStackRepository;
//    @Autowired
//    private SkillStackRepository skillStackRepository;
//
//    @Test
//    @Transactional
//    public void addDummyMentor() {
//        final int startInclusive = 100;
//        final int count = 200;
//        final int endExclusive = startInclusive + count;
//
//        List<UserAbstractAccount> userAbstractAccounts =  userAbstractAccountRepository.saveAll(IntStream.range(0, count).mapToObj(n->UserAbstractAccount.dummy()).collect(Collectors.toList()));
//        List<User> users = userRepository.saveAll(IntStream.range(startInclusive, endExclusive).mapToObj(n->User.dummy(n, userAbstractAccounts.get(n-startInclusive))).collect(Collectors.toList()));
//        List<Mentor> mentors = mentorRepository.saveAll(users.stream().map(Mentor::dummy).collect(Collectors.toList()));
//        List<MentorCareer> mentorCareers = mentorCareerRepository.saveAll(mentors.stream().map(MentorCareer::dummy).collect(Collectors.toList()));
//        IntStream.range(0, count).forEach(n->mentors.get(n).setMentorCareers(List.of(mentorCareers.get(n))));
//        List<MentorSkillStack> mentorSkillStacks = mentorSkillStackRepository.saveAll(mentors.stream()
//                .map(mentor->{
//                    SkillStack skillStack = skillStackRepository.findBySkillStackId(new Random().nextInt(32) + 1L).orElse(null);
//                    return MentorSkillStack.dummy(mentor, skillStack);
//                })
//                .collect(Collectors.toList()));
//       IntStream.range(0, count).forEach(n->mentors.get(n).setMentorSkillStacks(List.of(mentorSkillStacks.get(n))));
//
//       mentorRepository.saveAll(mentors);
//        Assertions.assertTrue(true);
//    }
//}
