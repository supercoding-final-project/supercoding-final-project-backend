package com.github.supercodingfinalprojectbackend.entity.type;

import java.util.Set;

public enum SkillStackCategoryType implements CustomEnum {
    FRAMEWORK(SkillStackType.SPRING),
    PROGRAMMING_LANGUAGE,
    BACKEND,
    FRONTEND,
    OBJECT_ORIENTED_PROGRAMMING,
    FUNCTIONAL_PROGRAMMING,

    DBA(SkillStackType.MYSQL,
        SkillStackType.MONGODB,
        SkillStackType.REDIS,
        SkillStackType.MARIA,
        SkillStackType.ORACLE
    );

    private final Set<SkillStackType> skillStackTypeSet;
    private final SkillStackCategoryType redirect;

    SkillStackCategoryType(SkillStackType... skillStackTypes) {
        this.redirect = null;
        this.skillStackTypeSet = skillStackTypes != null ? Set.of(skillStackTypes) : Set.of();
    }
    SkillStackCategoryType(SkillStackCategoryType redirect, SkillStackType... skillStackTypes) {
        this.redirect = redirect;
        this.skillStackTypeSet = skillStackTypes != null ? Set.of(skillStackTypes) : Set.of();
    }
    @Override
    public SkillStackCategoryType resolve() { return redirect != null ? redirect.resolve() : this; }

    public Set<SkillStackType> getSkillStackTypeSet() {
        return skillStackTypeSet;
    }
}
