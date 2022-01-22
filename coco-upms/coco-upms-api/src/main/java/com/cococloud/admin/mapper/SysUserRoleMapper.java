package com.cococloud.admin.mapper;

import com.cococloud.upms.common.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户角色表 Mapper 接口
 * </p>
 *
 * @author dzh
 * @since 2021-12-31
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
    boolean removeUserRoleByUserID(Integer userId);
}
