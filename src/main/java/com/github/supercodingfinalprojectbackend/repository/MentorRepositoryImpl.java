package com.github.supercodingfinalprojectbackend.repository;

import static com.github.supercodingfinalprojectbackend.entity.QMentor.mentor;
import static com.github.supercodingfinalprojectbackend.entity.QMentorSkillStack.mentorSkillStack;
import static com.github.supercodingfinalprojectbackend.entity.QSkillStack.skillStack;

import com.github.supercodingfinalprojectbackend.entity.Mentor;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MentorRepositoryImpl implements MentorRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Mentor> searchAll(String keyword, List<String> skillStackList){
		log.info("keyword searchAll : {}", keyword);

		List<Mentor> result = jpaQueryFactory
				.selectFrom(mentor)
				.leftJoin(mentor.mentorSkillStacks, mentorSkillStack)
				.leftJoin(mentorSkillStack.skillStack, skillStack)
				.where(mentorNameLike(keyword))
				.fetch();

		log.info("result : {}", result);
		return result;
	}

	private BooleanExpression conMentorSkillStack(List<String> skillStacks){
		return null;
	}

	private BooleanExpression mentorNameLike(String keyWord){
		return keyWord != null ? mentor.name.like("%" + keyWord + "%") : null;
	}
}
