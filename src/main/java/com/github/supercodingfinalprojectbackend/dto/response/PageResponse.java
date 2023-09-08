package com.github.supercodingfinalprojectbackend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponse<T> {
    @Schema(title = "다음 페이지의 존재 유무")
    private boolean hasNext;
    @Schema(title = "이전 페이지의 존재 유무")
    private boolean hasPrevious;
    @Schema(title = "전체 페이지 개수")
    private int totalPages;
    @Schema(title = "전체 데이터 개수")
    private long totalElements;
    @Schema(title = "조회된 데이터 리스트")
    private List<T> contents;

    public static <T> PageResponse<T> from(Page<T> page) {
        return PageResponse.<T>builder()
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .contents(page.getContent())
                .build();
    }
}
