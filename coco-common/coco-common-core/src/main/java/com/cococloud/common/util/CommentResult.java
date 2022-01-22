package com.cococloud.common.util;

import com.cococloud.common.constant.CommonConstans;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 传给前端页面的一个数据封装
 * @author duzhihui
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResult<T> {
    /**
     * 状态码  CommonConstans.SUCCESS表示成功，CommonConstans.FAIL表示失败
     */
    private Integer code;

    /**
     * 具体的消息，是成功还是异常
     */
    private String msg;

    /**
     * 具体的数据
     */
    private T data;

    public static <T> CommentResult<T> ok() {
        return new CommentResult<>(CommonConstans.SUCCESS, null, null);
    }

    public static <T> CommentResult<T> ok(T data) {
        return new CommentResult<>(CommonConstans.SUCCESS, null, data);
    }

    public static <T> CommentResult<T> ok(T data, String msg) {
        return new CommentResult<>(CommonConstans.SUCCESS, msg, data);
    }

    public static <T> CommentResult<T> failed() {
        return new CommentResult<>(CommonConstans.FAIL, null, null);
    }

    public static <T> CommentResult<T> failed(String msg) {
        return new CommentResult<>(CommonConstans.FAIL, msg, null);
    }

    public static <T> CommentResult<T> failed(T data) {
        return new CommentResult<>(CommonConstans.FAIL, null, data);
    }

    public static <T> CommentResult<T> failed(T data, String msg) {
        return new CommentResult<>(CommonConstans.FAIL, msg, data);
    }

    public CommentResult(Integer code, String msg) {
        this(code, msg, null);
    }
}
