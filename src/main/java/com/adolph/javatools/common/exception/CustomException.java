package com.adolph.javatools.common.exception;

import com.adolph.javatools.common.response.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomException extends RuntimeException{

    @Autowired
    ResultCodeEnum resultCode;

    // 带参构造
    public CustomException(ResultCodeEnum resultCode){
        this.resultCode = resultCode;
    }
    
    public ResultCodeEnum getResultCode(){
        return resultCode;
    }

    /**
     * 静态方法
     *      用法：CustomException.cast(ResultCodeEnum.FAIL);
     * @param resultCode
     */
    public static void cast(ResultCodeEnum resultCode){
        throw new CustomException(resultCode);
    }
}
