package com.graze.pastoral.notes.domain.dto.category;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/7/24 15:25
 */
@Data
public class CategoryOutput {
    private UUID id;

    @NotBlank
    private String name;

    private Integer recordTotal;
}
