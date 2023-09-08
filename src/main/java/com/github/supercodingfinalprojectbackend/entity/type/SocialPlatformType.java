package com.github.supercodingfinalprojectbackend.entity.type;

public enum SocialPlatformType implements CustomEnum {
    NONE,
    KAKAO,
    GOOGLE;

    private final SocialPlatformType redirect;
    SocialPlatformType() {
        this.redirect = null;
    }
    SocialPlatformType(SocialPlatformType redirect) {
        this.redirect = redirect;
    }

    @Override
    public SocialPlatformType resolve() { return redirect != null ? redirect.resolve() : this; }
}
