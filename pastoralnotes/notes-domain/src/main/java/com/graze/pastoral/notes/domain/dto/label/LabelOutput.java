package com.graze.pastoral.notes.domain.dto.label;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020-07-23 22:17
 */
@Data
public class LabelOutput {
    private UUID id;

    @NotBlank(message = "标签名不可为空")
    private String name;

    @NotBlank
    private String type;

    private Integer recordTotal;
}
