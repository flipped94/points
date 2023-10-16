package org.example.points.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {

    private Integer code;

    public BusinessException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

}
