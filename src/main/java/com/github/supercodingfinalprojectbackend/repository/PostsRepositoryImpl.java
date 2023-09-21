package com.github.supercodingfinalprojectbackend.repository;

import com.github.supercodingfinalprojectbackend.entity.Posts;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.github.supercodingfinalprojectbackend.entity.QPosts.posts;
import static com.github.supercodingfinalprojectbackend.entity.QPostsSkillStack.postsSkillStack;
import static com.github.supercodingfinalprojectbackend.entity.QSkillStack.skillStack;

@Repository
@RequiredArgsConstructor
public class PostsRepositoryImpl implements PostRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public Page<Posts> filterSearchPosts(String word,List<String> stackCategory, List<String> skillStackList, List<String> level, Pageable pageable) {
        List<Posts> postsList = jpaQueryFactory
                .selectFrom(posts)
                .leftJoin(posts.postsSkillStack, postsSkillStack)
                .leftJoin(postsSkillStack.skillStack,skillStack)
                .where(
                        posts.isDeleted.eq(false),
                        wordLike(word),
                        stackCategoryFiltering(stackCategory),
                        skillStackFiltering(skillStackList),
                        levelFiltering(level)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(posts.count())
                .from(posts)
                .leftJoin(posts.postsSkillStack, postsSkillStack)
                .leftJoin(postsSkillStack.skillStack,skillStack)
                .where(
                        posts.isDeleted.eq(false),
                        wordLike(word),
                        skillStackFiltering(skillStackList),
                        levelFiltering(level)
                );
        return PageableExecutionUtils.getPage(postsList,pageable,countQuery::fetchOne);
    }
    private BooleanExpression wordLike(String keyword){
        return keyword != null ? posts.title.like("%" + keyword + "%") : null;
    }
    private BooleanExpression stackCategoryFiltering(List<String> stackCategory){
        return stackCategory != null ? posts.postsSkillStack.skillStack.skillStackCategory.in(stackCategory) : null;
    }
    private BooleanExpression skillStackFiltering(List<String> skillStackList){
        return skillStackList != null ? skillStack.skillStackName.in(skillStackList) : null;
    }
    private BooleanExpression levelFiltering(List<String> levelList){
        return levelList != null ? posts.level.in(levelList) : null;
    }
}
