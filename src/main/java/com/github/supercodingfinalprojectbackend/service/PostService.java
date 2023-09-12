package com.github.supercodingfinalprojectbackend.service;

import com.github.supercodingfinalprojectbackend.dto.PostDto;
import com.github.supercodingfinalprojectbackend.entity.*;
import com.github.supercodingfinalprojectbackend.entity.type.PostContentType;
import com.github.supercodingfinalprojectbackend.entity.type.SkillStackType;
import com.github.supercodingfinalprojectbackend.exception.errorcode.PostErrorCode;
import com.github.supercodingfinalprojectbackend.repository.*;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {
    private final UserRepository userRepository;
    private final MentorRepository mentorRepository;
    private final PostsRepository postsRepository;
    private final PostsContentRepository postsContentRepository;
    private final PostsSkillStackRepository postsSkillStackRepository;
    private final SkillStackRepository skillStackRepository;

    @Transactional
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

        return ResponseUtils.created("포스트가 정상적으로 등록되었습니다.",null);
    }

    public ResponseEntity<ApiResponse<PostDto>> getPost(Long postId) {
        Posts posts = postsRepository.findById(postId).orElseThrow(PostErrorCode.POST_NOT_POST_ID::exception);
        List<PostsContent> contentList = postsContentRepository.findAllByPosts(posts);
        String stack = postsSkillStackRepository.findByPosts(posts).getSkillStack().getSkillStackName();

        return ResponseUtils.ok("정상적으로 포스트 조회 되었습니다.", PostDto.PostInfoResponse(posts,contentList,stack));
    }
}
