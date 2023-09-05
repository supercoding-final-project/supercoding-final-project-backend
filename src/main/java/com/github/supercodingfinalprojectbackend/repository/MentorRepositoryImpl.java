package com.github.supercodingfinalprojectbackend.repository;

import static com.github.supercodingfinalprojectbackend.entity.QMentor.mentor;
import static com.github.supercodingfinalprojectbackend.entity.QMentorSkillStack.mentorSkillStack;
import static com.github.supercodingfinalprojectbackend.entity.QSkillStack.skillStack;

import com.github.supercodingfinalprojectbackend.dto.MentorDto.MentorInfoResponse;
import com.github.supercodingfinalprojectbackend.dto.QMentorDto_MentorInfoResponse;
import com.github.supercodingfinalprojectbackend.entity.Mentor;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MentorRepositoryImpl implements MentorRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Mentor> searchAll(String keyword, List<String> skillStackList){

		return jpaQueryFactory
				.selectDistinct(mentor)
				.from(mentor)
				.leftJoin(mentor.mentorSkillStacks, mentorSkillStack)
				.leftJoin(mentorSkillStack.skillStack, skillStack)
				.where(conMentorSkillStack(keyword).or(mentorNameLike(keyword)))
				.fetch();
	}

	@Override
	public List<MentorInfoResponse> searchAllFromDto(String keyword, List<String> skillStacks){

		return jpaQueryFactory
				.selectDistinct(new QMentorDto_MentorInfoResponse(
						mentor.mentorId,
						mentor.name,
						mentor.introduction,
						mentor.company
				))
				.from(mentor)
				.leftJoin(mentor.mentorSkillStacks, mentorSkillStack)
				.leftJoin(mentorSkillStack.skillStack, skillStack)
				.where(conMentorSkillStack(keyword).or(mentorNameLike(keyword)))
				.fetch();
	}

	@Override
	public Page<MentorInfoResponse> searchAllFromDtoWithOffsetPagination(String keyword,
			List<String> skillStacks, Pageable pageable){

		// Querydsl fetchResults() , fetchCount() Deprecated
		// 대체 방법으로 해결

		List<MentorInfoResponse> content = jpaQueryFactory
				.selectDistinct(new QMentorDto_MentorInfoResponse(
						mentor.mentorId,
						mentor.name,
						mentor.introduction,
						mentor.company
				))
				.from(mentor)
				.leftJoin(mentor.mentorSkillStacks, mentorSkillStack)
				.leftJoin(mentorSkillStack.skillStack, skillStack)
				.where(conMentorSkillStack(keyword).or(mentorNameLike(keyword)))
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();

		JPAQuery<Long> countQuery = jpaQueryFactory
				.selectDistinct(mentor.count())
				.from(mentor)
				.leftJoin(mentor.mentorSkillStacks, mentorSkillStack)
				.leftJoin(mentorSkillStack.skillStack, skillStack)
				.where(conMentorSkillStack(keyword).or(mentorNameLike(keyword)));

		return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
	}

	@Override
	public Page<MentorInfoResponse> searchAllFromDtoWithCursorPagination(String keyword,
			List<String> skillStacks, Long cursorId, Pageable pageable){

		List<MentorInfoResponse> content = jpaQueryFactory
				.selectDistinct(new QMentorDto_MentorInfoResponse(
						mentor.mentorId,
						mentor.name,
						mentor.introduction,
						mentor.company
				))
				.from(mentor)
				.leftJoin(mentor.mentorSkillStacks, mentorSkillStack)
				.leftJoin(mentorSkillStack.skillStack, skillStack)
				.where(
						getRecordsWithCursorId(cursorId),
						mentorNameLike(keyword).or(conMentorSkillStack(keyword)),
						skillStackFiltering(skillStacks)
				)
				.limit(pageable.getPageSize())
				.fetch();

		JPAQuery<Long> countQuery = jpaQueryFactory
				.selectDistinct(mentor.count())
				.from(mentor)
				.leftJoin(mentor.mentorSkillStacks, mentorSkillStack)
				.leftJoin(mentorSkillStack.skillStack, skillStack)
				.where(
						getRecordsWithCursorId(cursorId),
						mentorNameLike(keyword).or(conMentorSkillStack(keyword)),
						skillStackFiltering(skillStacks)
				);

		return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
	}

	private BooleanExpression conMentorSkillStack(String keyword){
		return keyword != null ? skillStack.skillStackName.like("%" + keyword + "%") : null;
	}

	private BooleanExpression mentorNameLike(String keyword){
		return keyword != null ? mentor.name.like("%" + keyword + "%") : null;
	}

	private BooleanExpression skillStackFiltering(List<String> skillStackList){
		return skillStackList != null ? skillStack.skillStackName.in(skillStackList) : null;
	}

	private BooleanExpression getRecordsWithCursorId(Long cursorId){
		return cursorId != null ? mentor.mentorId.gt(cursorId) : null;
	}

}
