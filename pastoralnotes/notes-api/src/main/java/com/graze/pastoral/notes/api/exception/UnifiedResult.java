package com.graze.pastoral.notes.api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020/6/11 17:02
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class UnifiedResult<T> {

    private Boolean success = true;
    private String errorCode = "";
    private String errorMsg = "";
    private T resultBody;

    public UnifiedResult(T resultBody) {
        this.resultBody = resultBody;
    }
}
