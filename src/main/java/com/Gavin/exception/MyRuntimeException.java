package com.Gavin.exception;


import com.Gavin.common.StatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
public class MyRuntimeException extends RuntimeException implements Serializable {
    @Serial
    private static final long serialVersionUID = 2719764161468625856L;
    private StatusEnum statusEnum;

    public MyRuntimeException(StatusEnum statusEnum){
        super(statusEnum.getMsg());
        this.statusEnum=statusEnum;
    }
}
