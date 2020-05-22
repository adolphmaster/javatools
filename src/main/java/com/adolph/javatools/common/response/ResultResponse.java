package com.adolph.javatools.common.response;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * guoqi
 */
@Data
public class ResultResponse {
    private Boolean success;

    private Integer code;

    private String message;

    private Map<String, Object> data = new HashMap<>();

    /**
     * 构造器私有
     */
    private ResultResponse(){}

    /**
     * 通用返回成功
     * @return
     */
    public static ResultResponse ok() {
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        resultResponse.setCode(ResultCodeEnum.SUCCESS.getCode());
        resultResponse.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return resultResponse;
    }

    /**
     * 通用返回失败，未知错误
     * @return
     */
    public static ResultResponse error() {
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setSuccess(ResultCodeEnum.UNKNOWN_ERROR.getSuccess());
        resultResponse.setCode(ResultCodeEnum.UNKNOWN_ERROR.getCode());
        resultResponse.setMessage(ResultCodeEnum.UNKNOWN_ERROR.getMessage());
        return resultResponse;
    }

    /**
     * 设置结果，形参为结果枚举
     * @param result
     * @return
     */
    public static ResultResponse setResult(ResultCodeEnum result) {
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setSuccess(result.getSuccess());
        resultResponse.setCode(result.getCode());
        resultResponse.setMessage(result.getMessage());
        return resultResponse;
    }

    /**
     * 自定义返回数据
     * @param map
     * @return
     */
    public ResultResponse data(Map<String,Object> map) {
        this.setData(map);
        return this;
    }

    /**
     * 通用设置data
     * @param key
     * @param value
     * @return
     */
    public ResultResponse data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    /**
     *  自定义状态信息
     * @param message
     * @return
     */
    public ResultResponse message(String message) {
        this.setMessage(message);
        return this;
    }

    /**
     * 自定义状态码
     * @param code
     * @return
     */
    public ResultResponse code(Integer code) {
        this.setCode(code);
        return this;
    }

    /**
     * 自定义返回结果
     * @param success
     * @return
     */
    public ResultResponse success(Boolean success) {
        this.setSuccess(success);
        return this;
    }
}

