package com.cococloud.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cococloud.upms.common.dto.UserDTO;
import com.cococloud.upms.common.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cococloud.upms.common.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author dzh
 * @since 2021-12-31
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * XML 自定义分页, 分页查询用户信息
     */
    IPage<UserVO> selectUserPageVo(Page<?> page, @Param("query") UserDTO query);
}
