package com.Gavin.exception;

import com.Gavin.common.ExceptionMessageBean;
import com.Gavin.common.StatusEnum;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyGlobalExceptionHanlder {

    @ExceptionHandler(MyRuntimeException.class)
    public ExceptionMessageBean handleMyRuntimeException(MyRuntimeException exception){
        ExceptionMessageBean messageBean=new ExceptionMessageBean();
        StatusEnum statusEnum = exception.getStatusEnum();
        messageBean.setMsg(statusEnum.getMsg());
        messageBean.setStatus(statusEnum.getStatus());
        return messageBean;
    }

    @ExceptionHandler(Throwable.class)
    public ExceptionMessageBean handleUnknownError(Throwable throwable){
        ExceptionMessageBean messageBean=new ExceptionMessageBean();
        StatusEnum statusEnum = StatusEnum.UNKNOWN_ERROR;
        String message=throwable.getMessage();
        if (message==null){
            message = statusEnum.getMsg();
        }
        messageBean.setMsg(message);
        messageBean.setStatus(statusEnum.getStatus());
        return messageBean;
    }
}
