package com.cococloud.admin.service;

import com.cococloud.upms.common.dto.UserDTO;
import com.cococloud.upms.common.dto.UserDetailsDto;
import com.cococloud.upms.common.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cococloud.upms.common.vo.UserInfoVo;
import com.cococloud.upms.common.vo.UserVO;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author dzh
 * @since 2021-12-31
 */
public interface SysUserService extends IService<SysUser> {

    UserDetailsDto getUserDetails(SysUser sysUser);

    UserInfoVo getUserInfo(SysUser sysUser);

    boolean saveUser(UserDTO userVO);

    boolean deleteUser(SysUser userId);

    boolean updateUser(UserDTO userDTO);

    boolean updateCurrUserInfo(UserDTO userDTO);
}
