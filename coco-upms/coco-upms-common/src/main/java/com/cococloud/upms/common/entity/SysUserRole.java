package com.cococloud.upms.common.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户角色表
 * </p>
 *
 * @author dzh
 * @since 2021-12-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUserRole implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 角色ID
     */
    private Integer roleId;


}
