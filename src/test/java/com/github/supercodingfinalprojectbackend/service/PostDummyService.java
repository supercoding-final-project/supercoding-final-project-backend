//package com.github.supercodingfinalprojectbackend.service;
//
//import com.github.supercodingfinalprojectbackend.entity.*;
//import com.github.supercodingfinalprojectbackend.repository.*;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Random;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//@SpringBootTest
//public class PostDummyService {
//
//    @Autowired
//    private PostsRepository postsRepository;
//    @Autowired
//    private MentorRepository mentorRepository;
//    @Autowired
//    private PostsContentRepository postsContentRepository;
//    @Autowired
//    private PostsSkillStackRepository postsSkillStackRepository;
//    @Autowired
//    private SkillStackRepository skillStackRepository;
//    @Autowired
//    private ReviewRepository reviewRepository;
//
//    @Test
//    @Transactional
//    public void addDummyPost() {
//        final int startInclusive = 1412;
//        final int count = 200;
//
//        List<Mentor>mentors = mentorRepository.findAllByMentorIdInAndIsDeletedIsFalseOrderByMentorIdAsc(IntStream.range(startInclusive, startInclusive + count).mapToObj(Long::valueOf).collect(Collectors.toList()));
//        List<Posts> posts = postsRepository.saveAll(mentors.stream().map(Posts::dummy).collect(Collectors.toList()));
//        List<PostsContent> postsContents = postsContentRepository.saveAll(posts.stream().map(PostsContent::dummy).collect(Collectors.toList()));
//        List<PostsSkillStack> postsSkillStacks = postsSkillStackRepository.saveAll(
//                posts.stream()
//                        .map(post->{
//                            SkillStack skillStack = skillStackRepository.findBySkillStackId(new Random().nextInt(32) + 1L).orElse(null);
//                            return PostsSkillStack.dummy(post, skillStack);
//                        })
//                        .collect(Collectors.toList())
//        );
//    }
//}
