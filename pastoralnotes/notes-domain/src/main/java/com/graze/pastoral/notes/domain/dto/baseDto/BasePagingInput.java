package com.graze.pastoral.notes.domain.dto.baseDto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/7/22 12:39
 */
@Data
public class BasePagingInput {

    @NotNull
    @Min(value = 1, message = "页码必须大于1")
    private Integer pageIndex;

    @NotNull
    @Min(value = 1, message = "每页数量必须在1~1000之间")
    @Max(value = 1000, message = "每页数量必须在1~1000之间")
    private Integer pageSize;

}
