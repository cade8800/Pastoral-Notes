package com.graze.pastoral.notes.domain.dto.baseDto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/7/22 12:38
 */
@Getter
@Setter
public class BasePagingOutput<T> {
    private List<T> content = new ArrayList<>();
    private Long totalElements;
    private Integer totalPages;
    private Integer pageNumber;
    private Integer numberOfElements;
}
