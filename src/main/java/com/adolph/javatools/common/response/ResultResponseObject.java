package com.adolph.javatools.common.response;

import lombok.Data;

/**
 * @Author guoqi
 * @Date 2020/5/25 14:32
 * @Description   返回格式如下：
    {
        "success": true,
        "code": 5000,
        "message": "成功",
        "data": [{"id": 20,……},{"id": 21,……},……]
    }
 **/
@Data
public class ResultResponseObject {
    private Boolean success;

    private Integer code;

    private String message;

    private Object data;

    /**
     * 构造器私有
     */
    private ResultResponseObject(){}

    /**
     * 通用返回成功
     * @return
     */
    public static ResultResponseObject ok() {
        ResultResponseObject ResultResponseObject = new ResultResponseObject();
        ResultResponseObject.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        ResultResponseObject.setCode(ResultCodeEnum.SUCCESS.getCode());
        ResultResponseObject.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return ResultResponseObject;
    }

    /**
     * 通用返回失败，未知错误
     * @return
     */
    public static ResultResponseObject error() {
        ResultResponseObject ResultResponseObject = new ResultResponseObject();
        ResultResponseObject.setSuccess(ResultCodeEnum.UNKNOWN_ERROR.getSuccess());
        ResultResponseObject.setCode(ResultCodeEnum.UNKNOWN_ERROR.getCode());
        ResultResponseObject.setMessage(ResultCodeEnum.UNKNOWN_ERROR.getMessage());
        return ResultResponseObject;
    }

    /**
     * 设置结果，形参为结果枚举
     * @param result
     * @return
     */
    public static ResultResponseObject setResult(ResultCodeEnum result) {
        ResultResponseObject ResultResponseObject = new ResultResponseObject();
        ResultResponseObject.setSuccess(result.getSuccess());
        ResultResponseObject.setCode(result.getCode());
        ResultResponseObject.setMessage(result.getMessage());
        return ResultResponseObject;
    }

    /**
     * 自定义返回数据
     * @param data
     * @return
     */
    public ResultResponseObject data(Object data) {
        this.setData(data);
        return this;
    }
    
    /**
     *  自定义状态信息
     * @param message
     * @return
     */
    public ResultResponseObject message(String message) {
        this.setMessage(message);
        return this;
    }

    /**
     * 自定义状态码
     * @param code
     * @return
     */
    public ResultResponseObject code(Integer code) {
        this.setCode(code);
        return this;
    }

    /**
     * 自定义返回结果
     * @param success
     * @return
     */
    public ResultResponseObject success(Boolean success) {
        this.setSuccess(success);
        return this;
    }
}

