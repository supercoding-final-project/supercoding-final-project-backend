package com.github.supercodingfinalprojectbackend.service;

import com.github.supercodingfinalprojectbackend.dto.PostDto;
import com.github.supercodingfinalprojectbackend.entity.*;
import com.github.supercodingfinalprojectbackend.entity.type.PostContentType;
import com.github.supercodingfinalprojectbackend.entity.type.SkillStackType;
import com.github.supercodingfinalprojectbackend.entity.type.WeekType;
import com.github.supercodingfinalprojectbackend.exception.errorcode.PostErrorCode;
import com.github.supercodingfinalprojectbackend.repository.*;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class PostService {
    private final MentorRepository mentorRepository;
    private final PostsRepository postsRepository;
    private final PostsContentRepository postsContentRepository;
    private final PostsSkillStackRepository postsSkillStackRepository;
    private final SkillStackRepository skillStackRepository;

    private final MentorScheduleTemplateRepository mentorScheduleTemplateRepository;

    public ResponseEntity<ApiResponse<Void>> createPost(PostDto postDto, Long userId) {
        Mentor mentor = mentorRepository.findByUserUserIdAndIsDeletedIsFalse(userId).get();
        Posts entity = Posts.fromDto(postDto,mentor);
        Posts post = postsRepository.save(entity);

        List<String> workCareerList = postDto.getWorkCareer();
        List<String> educateCareerList = postDto.getEducateCareer();
        List<String> reviewStyleList = postDto.getReviewStyle();

        for (String workCareer : workCareerList) {
            PostsContent postsContent = PostsContent.fromPost(workCareer, PostContentType.WORK_CAREER.name(), post);
            postsContentRepository.save(postsContent);
        }

        for (String educateCareer : educateCareerList) {
            PostsContent postsContent = PostsContent.fromPost(educateCareer, PostContentType.EDUCATE_CAREER.name(), post);
            postsContentRepository.save(postsContent);
        }
        for (String reviewStyle : reviewStyleList) {
            PostsContent postsContent = PostsContent.fromPost(reviewStyle, PostContentType.REVIEW_STYLE.name(), post);
            postsContentRepository.save(postsContent);
        }

        SkillStackType skillStackType = SkillStackType.findBySkillStackType(postDto.getPostStack());
        SkillStack skillStack = skillStackRepository.findBySkillStackName(skillStackType.getSkillStackName());
        PostsSkillStack postsSkillStack = PostsSkillStack.fromPost(post,skillStack);
        postsSkillStackRepository.save(postsSkillStack);

        return ResponseUtils.created("멘티 모집이 정상적으로 등록되었습니다.",null);
    }
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<PostDto>> getPost(Long postId) {
        Posts posts = postsRepository.findByPostIdAndIsDeletedFalse(postId).orElseThrow(PostErrorCode.POST_NOT_POST_ID::exception);
        List<PostsContent> contentList = postsContentRepository.findAllByPosts(posts);
        String stack = postsSkillStackRepository.findByPosts(posts).getSkillStack().getSkillStackName();

        return ResponseUtils.ok("정상적으로 멘티 모집 조회 되었습니다.", PostDto.PostInfoResponse(posts,contentList,stack));
    }

    public ResponseEntity<ApiResponse<Void>> updatePost(Long postId, PostDto postDto) {
        Posts posts = postsRepository.findByPostIdAndIsDeletedFalse(postId).orElseThrow(PostErrorCode.POST_NOT_POST_ID::exception);
        posts.postsUpdate(postDto);

        List<PostsContent> postsContentList = postsContentRepository.findAllByPosts(posts);

        for (PostsContent postsContent : postsContentList) {
            postsContent.postContentIsDeleted();
            postsContentRepository.save(postsContent);
        }

        List<String> workCareerList = postDto.getWorkCareer();
        List<String> educateCareerList = postDto.getEducateCareer();
        List<String> reviewStyleList = postDto.getReviewStyle();

        for (String workCareer : workCareerList) {
            PostsContent postsContent = PostsContent.fromPost(workCareer, PostContentType.WORK_CAREER.name(), posts);
            postsContentRepository.save(postsContent);
        }

        for (String educateCareer : educateCareerList) {
            PostsContent postsContent = PostsContent.fromPost(educateCareer, PostContentType.EDUCATE_CAREER.name(), posts);
            postsContentRepository.save(postsContent);
        }
        for (String reviewStyle : reviewStyleList) {
            PostsContent postsContent = PostsContent.fromPost(reviewStyle, PostContentType.REVIEW_STYLE.name(), posts);
            postsContentRepository.save(postsContent);
        }

        PostsSkillStack postsSkillStack = postsSkillStackRepository.findByPosts(posts);
        SkillStack skillStack = skillStackRepository.findBySkillStackName(postDto.getPostStack());
        postsSkillStack.skillStackUpdate(skillStack);
        return ResponseUtils.ok("멘티 모집이 정상적으로 수정되었습니다.",null);
    }

    public ResponseEntity<ApiResponse<Void>> deletePost(Long postId) {
        Posts posts = postsRepository.findByPostIdAndIsDeletedFalse(postId).orElseThrow(PostErrorCode.POST_NOT_POST_ID::exception);
        posts.postsIsDeleted();

        List<PostsContent> postsContentList = postsContentRepository.findAllByPosts(posts);

        for (PostsContent postsContent : postsContentList) {
            postsContent.postContentIsDeleted();
            postsContentRepository.save(postsContent);
        }

        PostsSkillStack postsSkillStack = postsSkillStackRepository.findByPosts(posts);
        postsSkillStack.postsSkillStackIsDeleted();
        return ResponseUtils.noContent("멘티 모집이 정삭적으로 삭제되었습니다.",null);
    }


    public ResponseEntity<ApiResponse<List<Integer>>> getTimes(Long postId, String days) {
        Posts posts = postsRepository.findByPostIdAndIsDeletedFalse(postId).orElseThrow(PostErrorCode.POST_NOT_POST_ID::exception);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        LocalDate date = LocalDate.parse(days,formatter);

        String name = date.getDayOfWeek().name();
        String weekType = WeekType.valueOf(name).getName();

        List<MentorScheduleTemplate> scheduleList = mentorScheduleTemplateRepository.findByMentorAndScheduleWeek(posts.getMentor(),weekType);
        List<Integer> timeList = scheduleList.stream()
                .map(MentorScheduleTemplate::getValidTime)
                .collect(Collectors.toList());

        return ResponseUtils.ok("멘토의 신청가능한 시간이 조회되었습니다.",timeList);
    }
}

