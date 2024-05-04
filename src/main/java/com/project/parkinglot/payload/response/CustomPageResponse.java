package com.project.parkinglot.payload.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Represents a custom page response.
 * @param <T> the type of content in the response.
 */
@Getter
@Builder
public class CustomPageResponse<T> {

    private List<T> content;

    private Integer pageNumber;

    private Integer pageSize;

    private Integer totalPageCount;

    private Long totalElementCount;


    /**
     * Creates a custom page response from a Spring Data {@link Page} object.
     *
     * @param page the Page object
     * @param <T>  the type of content in the page
     * @return a CustomPageResponse object
     */
    public static <T> CustomPageResponse<T> of(Page<T> page) {
        return CustomPageResponse.<T>builder()
                .content(page.getContent())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalPageCount(page.getTotalPages())
                .totalElementCount(page.getTotalElements())
                .build();
    }

}
