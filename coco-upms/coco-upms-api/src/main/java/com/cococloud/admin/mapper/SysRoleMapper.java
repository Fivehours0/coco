package com.cococloud.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cococloud.upms.common.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cococloud.upms.common.vo.RoleVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 系统角色表 Mapper 接口
 * </p>
 *
 * @author dzh
 * @since 2021-12-31
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 根据userId找到该user对应的角色信息
     * @param userId 所使用的userId
     * @return 该user对应的SysRole
     */
    List<SysRole> listRoleByUserId(Integer userId);
}
