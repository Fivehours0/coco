package com.cococloud.admin.component;

import com.cococloud.common.util.CommentResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class UpmsExceptionHandler {

    /**
     * 处理业务校验过程中碰到的非法参数异常 该异常基本由{@link org.springframework.util.Assert}抛出
     * @see Assert#hasLength(String, String)
     * @see Assert#hasText(String, String)
     * @see Assert#isTrue(boolean, String)
     * @see Assert#isNull(Object, String)
     * @see Assert#notNull(Object, String)
     * @param exception 参数校验异常
     * @return API返回结果对象包装后的错误输出结果
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommentResult handleIllegalArgumentException (IllegalArgumentException exception){
        log.error("非法请求参数, {}", exception.getMessage(), exception);
        return CommentResult.failed(exception.getMessage());
    }

}
