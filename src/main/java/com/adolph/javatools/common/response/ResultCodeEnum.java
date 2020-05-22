package com.adolph.javatools.common.response;

import lombok.Getter;

/**
 * guoqi
 */
@Getter
public enum ResultCodeEnum {
    SUCCESS(true,5000,"成功"),
    FAIL(false,5001,"失败"),
    UNKNOWN_ERROR(false,5002,"未知错误"),
    PARAM_ERROR(false,5003,"参数错误") ,
    NULL_POINTER(false,5004,"空指针异常")
    ;

    // 响应是否成功
    private Boolean success;
    // 响应状态码
    private Integer code;
    // 响应信息
    private String message;

    ResultCodeEnum(boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}

