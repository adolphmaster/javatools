package com.adolph.javatools.common.exception;

import com.adolph.javatools.common.response.ResultCodeEnum;
import com.adolph.javatools.common.response.ResultResponse;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author guoqi
 * @Date 2020/5/22 14:30
 * @Description 全局异常处理类
 **/
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 定义map，存贮常见错误信息。该类map不可修改
     */
    private static ImmutableMap<Class<? extends Throwable>, ResultCodeEnum> EXCEPTIONS;
    /**
     * 构建ImmutableMap
     */
    protected static ImmutableMap.Builder<Class<? extends Throwable>,ResultCodeEnum> builder = ImmutableMap.builder();

    /**
     * 捕获CustomException类异常
     * @param customException
     * @return 
     */
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResultResponse customException(CustomException customException){
        ResultCodeEnum resultCode = customException.getResultCode();
        return ResultResponse.setResult(resultCode);
    }

    /**
     * 空指针异常处理方法
     * @param e
     * @return
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public ResultResponse error(NullPointerException e) {
        log.error(ExceptionUtil.getMessage(e));
        return ResultResponse.setResult(ResultCodeEnum.NULL_POINTER);
    }

    /**
     * 捕获非自定义类异常
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultResponse exception(Exception exception){
        // 记录日志
        log.error(ExceptionUtil.getMessage(exception));
        if (EXCEPTIONS == null){
            EXCEPTIONS = builder.build();
        }
        ResultCodeEnum resultCode = EXCEPTIONS.get(exception.getClass());
        if (resultCode != null){
            return ResultResponse.setResult(resultCode);
        }else {
            return ResultResponse.setResult(ResultCodeEnum.UNKNOWN_ERROR);
        }
    }

    static {
        builder.put(HttpMessageNotReadableException.class, ResultCodeEnum.PARAM_ERROR);
    }
}

