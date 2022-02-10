package com.cococloud.security.component;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 对token进行增强，添加的简要用户信息
 */
@Data
@AllArgsConstructor
public class BriefUser implements Serializable {
    /**
     * 用户id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;
}
