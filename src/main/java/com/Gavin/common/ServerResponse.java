package com.Gavin.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
public class ServerResponse<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = -2903423000605908420L;
    private String status;
    private String msg;
    private T data;

    private ServerResponse(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private ServerResponse(String status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ServerResponse<T> success(){
        return new ServerResponse<>(StatusEnum.SUCCESS.getStatus(), StatusEnum.SUCCESS.getMsg());
    }
    public static <T> ServerResponse<T> success(T data) {
        return new ServerResponse<>(StatusEnum.SUCCESS.getStatus(), StatusEnum.SUCCESS.getMsg(), data);
    }

    public static <T> ServerResponse<T> success(StatusEnum statusEnum, T data) {
        return new ServerResponse<>(statusEnum.getStatus(), statusEnum.getMsg(), data);
    }

    public static <T> ServerResponse<T> error() {
        return new ServerResponse<>(StatusEnum.ERROR.getStatus(), StatusEnum.ERROR.getMsg());
    }

    public static <T> ServerResponse<T> error(StatusEnum statusEnum) {
        return new ServerResponse<>(statusEnum.getStatus(), statusEnum.getMsg());
    }
}
