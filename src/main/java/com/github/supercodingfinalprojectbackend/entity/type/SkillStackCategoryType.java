package com.github.supercodingfinalprojectbackend.entity.type;

import java.util.Set;

public enum SkillStackCategoryType implements CustomEnum {
    BACKEND(
            SkillStackType.JAVA,
            SkillStackType.SPRING,
            SkillStackType.JPA,
            SkillStackType.SPRING_BOOT,
            SkillStackType.SPRING_JDBC,
            SkillStackType.SQL,
            SkillStackType.DBMS_RDBMS,
            SkillStackType.REDIS,
            SkillStackType.POSTGRE_SQL,
            SkillStackType.MSA
    ),
    FRONTEND(
            SkillStackType.REACT,
            SkillStackType.REACT_NATIVE,
            SkillStackType.VUE,
            SkillStackType.TYPE_SCRIPT,
            SkillStackType.NEXT_JS,
            SkillStackType.SVELTE,
            SkillStackType.JAVA_SCRIPT
    ),
    INFRA(
            SkillStackType.KUBERNETES,
            SkillStackType.JENKINS,
            SkillStackType.GITHUB_ACTION,
            SkillStackType.HARBOR,
            SkillStackType.DOCKER
    ),
    PROGRAMMING_LANGUAGE(
            SkillStackType.PYTHON,
            SkillStackType.KOTLIN,
            SkillStackType.C_PLUS_PLUS,
            SkillStackType.TYPE_SCRIPT,
            SkillStackType.JAVA,
            SkillStackType.JAVA_SCRIPT,
            SkillStackType.RUBY,
            SkillStackType.GO,
            SkillStackType.RUST
    ),
    DBA(
            SkillStackType.DBMS_RDBMS,
            SkillStackType.MYSQL,
            SkillStackType.MONGODB,
            SkillStackType.MARIA,
            SkillStackType.ORACLE
    )
    ;

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
