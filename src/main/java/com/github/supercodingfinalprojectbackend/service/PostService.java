package com.github.supercodingfinalprojectbackend.service;

import com.github.supercodingfinalprojectbackend.dto.PostDto;
import com.github.supercodingfinalprojectbackend.dto.PostDto.OrderCodeReviewDto;
import com.github.supercodingfinalprojectbackend.dto.PostDto.PostSearchDto;
import com.github.supercodingfinalprojectbackend.dto.PostDto.PostTimeDto;
import com.github.supercodingfinalprojectbackend.dto.PostDto.PostTimeResponseDto;
import com.github.supercodingfinalprojectbackend.entity.*;
import com.github.supercodingfinalprojectbackend.entity.type.PostContentType;
import com.github.supercodingfinalprojectbackend.entity.type.SkillStackType;
import com.github.supercodingfinalprojectbackend.entity.type.WeekType;
import com.github.supercodingfinalprojectbackend.exception.ApiException;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.repository.*;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    private final MenteeRepository menteeRepository;
    private final OrderSheetRepository orderSheetRepository;

    private final SelectedClassTimeRepository selectedClassTimeRepository;

    public ResponseEntity<ApiResponse<Long>> createPost(PostDto postDto, Long userId) {
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

        SkillStackType skillStackType = SkillStackType.valueOf(postDto.getPostStack());
        SkillStack skillStack = skillStackRepository.findBySkillStackName(skillStackType.name()).orElseThrow(ApiErrorCode.INVALID_SKILL_STACK::exception);
        PostsSkillStack postsSkillStack = PostsSkillStack.fromPost(post,skillStack);
        postsSkillStackRepository.save(postsSkillStack);

        return ResponseUtils.created("멘티 모집이 정상적으로 등록되었습니다.",post.getPostId());
    }
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<PostDto>> getPost(Long postId, Long userId) {
        Posts posts = postsRepository.findByPostIdAndIsDeletedFalse(postId).orElseThrow(ApiErrorCode.POST_NOT_POST_ID::exception);
        List<PostsContent> contentList = postsContentRepository.findAllByPosts(posts);
        SkillStack stack = postsSkillStackRepository.findByPosts(posts).getSkillStack();
        boolean permission = userId != 0 && Objects.equals(posts.getMentor().getUser().getUserId(), userId);

        return ResponseUtils.ok("정상적으로 멘티 모집 조회 되었습니다.", PostDto.PostInfoResponse(posts,contentList,stack,permission));
    }

    public ResponseEntity<ApiResponse<Void>> updatePost(Long userId, Long postId, PostDto postDto) {
        Posts posts = postsRepository.findByPostIdAndIsDeletedFalse(postId).orElseThrow(ApiErrorCode.POST_NOT_POST_ID::exception);
        Mentor mentor = mentorRepository.findByUserUserIdAndIsDeletedIsFalse(userId).get();
        if(posts.getMentor().getMentorId() != mentor.getMentorId())throw new ApiException(ApiErrorCode.POST_NOT_MATCH_MENTOR);

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
        SkillStack skillStack = skillStackRepository.findBySkillStackName(postDto.getPostStack()).orElseThrow(ApiErrorCode.INVALID_SKILL_STACK::exception);
        postsSkillStack.skillStackUpdate(skillStack);
        return ResponseUtils.ok("멘티 모집이 정상적으로 수정되었습니다.",null);
    }

    public ResponseEntity<ApiResponse<Void>> deletePost(Long userId, Long postId) {
        Posts posts = postsRepository.findByPostIdAndIsDeletedFalse(postId).orElseThrow(ApiErrorCode.POST_NOT_POST_ID::exception);
        Mentor mentor = mentorRepository.findByUserUserIdAndIsDeletedIsFalse(userId).get();
        if(posts.getMentor().getMentorId() != mentor.getMentorId())throw new ApiException(ApiErrorCode.POST_NOT_MATCH_MENTOR);

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


    public ResponseEntity<ApiResponse<PostTimeResponseDto>> getTimes(Long postId, String days) {
        Posts posts = postsRepository.findByPostIdAndIsDeletedFalse(postId).orElseThrow(ApiErrorCode.POST_NOT_POST_ID::exception);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        LocalDate date = LocalDate.parse(days,formatter);

        String name = date.getDayOfWeek().name();
        String weekType = WeekType.valueOf(name).getName();

        List<MentorScheduleTemplate> scheduleList = mentorScheduleTemplateRepository.findByMentorAndScheduleWeek(posts.getMentor(),weekType);
        List<Integer> timeList = scheduleList.stream()
                .map(MentorScheduleTemplate::getValidTime)
                .collect(Collectors.toList());

        return ResponseUtils.ok("멘토의 신청가능한 시간이 조회되었습니다.", PostTimeResponseDto.timeResponseDto(timeList));
    }
    public ResponseEntity<ApiResponse<List<Integer>>> orderCodeReview(OrderCodeReviewDto orderCodeReviewDto, Long userId) {
        Mentee mentee = menteeRepository.findByUserUserIdAndIsDeletedIsFalse(userId).orElseThrow(ApiErrorCode.NOT_FOUND_MENTEE::exception);
        Posts posts = postsRepository.findByPostIdAndIsDeletedFalse(orderCodeReviewDto.getPostId()).orElseThrow(ApiErrorCode.POST_NOT_POST_ID::exception);

        OrderSheet orderSheet = orderSheetRepository.save(OrderSheet.of(orderCodeReviewDto, mentee, posts));
        List<PostTimeDto> timeDtoList = orderCodeReviewDto.getSelectTime();
        List<SelectedClassTime> timeList = new ArrayList<>();
        List<Integer> mentorReservationList = new ArrayList<>();
        List<Integer> menteeReservationList = new ArrayList<>();

        for (PostTimeDto postTimeDto : timeDtoList) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
            LocalDate date = LocalDate.parse(postTimeDto.getDay(), formatter);
            List<Integer> list = postTimeDto.getTimeList();

            mentorReservationList.addAll(list.stream()
                    .filter(time -> selectedClassTimeRepository.findAllByMentorAndDayAndIsDeletedIsFalse(posts.getMentor(), date.getDayOfMonth()).stream()
                            .map(SelectedClassTime::getHour)
                            .collect(Collectors.toList()).contains(time))
                    .collect(Collectors.toList()));

            menteeReservationList.addAll(list.stream()
                    .filter(time -> selectedClassTimeRepository.findAllByMenteeAndDayAndIsDeletedIsFalse(mentee, date.getDayOfMonth()).stream()
                            .map(SelectedClassTime::getHour)
                            .collect(Collectors.toList()).contains(time))
                    .collect(Collectors.toList()));

            if (mentorReservationList.size() == 0 && menteeReservationList.size() == 0) {
                timeList.addAll(list.stream()
                        .map(time -> SelectedClassTime.of(date, time, posts.getMentor(), mentee, orderSheet))
                        .collect(Collectors.toList())
                );
            }
        }

        if (mentorReservationList.size() != 0 ){
            throw new ApiException(HttpStatus.CONFLICT,"이미 예약이 완료된 시간입니다. 예약이 완료된 시간:"+mentorReservationList);
        } else if (menteeReservationList.size() != 0) {
            throw new ApiException(HttpStatus.CONFLICT,"동일한 시간에 이미 코드리뷰가 예약되어 있습니다. 예약된 시간:"+menteeReservationList);
        }else {
            selectedClassTimeRepository.saveAll(timeList);
        }

        UserAbstractAccount account = mentee.getUser().getAbstractAccount();
        if(account.getPaymoney()>=orderCodeReviewDto.getTotalPrice()){
            account.spendPayMoney(orderCodeReviewDto.getTotalPrice());
        }else{
            throw ApiErrorCode.MENTEE_ACCOUNT_NOT_ENOUGH.exception();
        }

        return ResponseUtils.noContent("코드리뷰 신청이 완료되었습니다.", null);
    }

    public ResponseEntity<ApiResponse<PostSearchDto>> searchMentorPost(Long mentorId, Integer page, Integer size) {
        Mentor mentor = mentorRepository.findByMentorIdAndIsDeletedIsFalse(mentorId).orElseThrow(ApiErrorCode.NOT_FOUND_MENTOR::exception);
        Pageable pageable = PageRequest.of(page,size);

        Page<Posts> posts = postsRepository.findAllByMentorAndIsDeletedFalse(mentor,pageable);
        return ResponseUtils.ok("검색이 완료되었습니다.",fromList(posts));
    }

    public ResponseEntity<ApiResponse<PostSearchDto>> searchPost(String word, List<String> stackCategory, List<String> skillStack, List<String> level, int page, Integer size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Posts> postsList = postsRepository.filterSearchPosts(word,stackCategory,skillStack,level,pageable);
        return ResponseUtils.ok("검색이 완료되었습니다.",fromList(postsList));
    }

    public PostSearchDto fromList(Page<Posts> posts){

        List<PostDto> postDtoList = new ArrayList<>();

        for (Posts post : posts) {
            List<PostsContent> contentList = postsContentRepository.findAllByPosts(post);
            SkillStack stack = postsSkillStackRepository.findByPosts(post).getSkillStack();

            postDtoList.add(PostDto.PostInfoResponse(post,contentList,stack,false));
        }

        return PostSearchDto.searchDto(postDtoList,posts);
    }
}