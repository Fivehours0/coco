package com.cococloud.admin.mapper;

import com.cococloud.upms.common.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author dzh
 * @since 2021-12-31
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * 根据roleId获取SysMenu
     */
    List<SysMenu> loadMenuByRoleId(Integer roleId);
}
