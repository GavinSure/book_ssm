package com.Gavin.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusEnum {

    SUCCESS("1001", "success"),
    ERROR("1002", "ERROR"),
    UNKNOWN_ERROR("1000", "未知错误"),

    //与admin相关的一些信息
    ADMIN_NOT_EXITS("2001", "用户不存在此系统"),
    ADMIN_NOT_ACTIVE("2002", "邮箱未激活"),
    ADMIN_ALREADY_ACTIVE("2003", "邮箱已经被激活"),
    CODE_TIMEOUT("2004", "邮箱验证码已经超时"),
    CODE_ERROR("2005", "验证码数据错误"),
    NO_LOGIN("3333", "token超时/恶意操作/解析异常"),

    //与图书相关的一些信息
    BOOKTYPE_NOT_SELECTER("3334","未选择图书分类");
    private final String status;
    private final String msg;
}
