package com.cococloud.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cococloud.admin.mapper.SysUserRoleMapper;
import com.cococloud.admin.service.SysMenuService;
import com.cococloud.common.constant.CacheConstants;
import com.cococloud.common.constant.CommonConstans;
import com.cococloud.common.constant.enums.MenuTypeEnum;
import com.cococloud.upms.common.dto.UserDTO;
import com.cococloud.upms.common.dto.UserDetailsDto;
import com.cococloud.upms.common.entity.SysMenu;
import com.cococloud.upms.common.entity.SysRole;
import com.cococloud.upms.common.entity.SysUser;
import com.cococloud.admin.mapper.SysRoleMapper;
import com.cococloud.admin.mapper.SysUserMapper;
import com.cococloud.admin.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cococloud.upms.common.entity.SysUserRole;
import com.cococloud.upms.common.vo.UserInfoVo;
import com.cococloud.upms.common.vo.UserVO;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Wrapper;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author dzh
 * @since 2021-12-31
 */
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    private final SysUserMapper userMapper;

    private final SysRoleMapper sysRoleMapper;

    private final SysMenuService sysMenuService;

    private final SysUserRoleMapper userRoleMapper;

    @Override
    public UserDetailsDto getUserDetails(SysUser sysUser) {
        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setSysUser(sysUser);
        // 角色信息
        List<SysRole> sysRole = sysRoleMapper.listRoleByUserId(sysUser.getUserId());
        userDetailsDto.setRoleList(sysRole);

        // 获取所有sysRole的id
        List<Integer> roleIds = sysRole.stream().map(SysRole::getRoleId).collect(Collectors.toList());
        userDetailsDto.setRoleId(roleIds);

        // 获取角色所对应的菜单访问权限
        String[] permissions = roleIds.stream().map(sysMenuService::loadMenuByRoleId).flatMap(Collection::stream)
                .filter(menu -> MenuTypeEnum.BUTTON.getType().equals(menu.getType())).map(SysMenu::getPermission)
                .filter(StrUtil::isNotBlank).toArray(String[]::new);
        userDetailsDto.setPermission(permissions);

        return userDetailsDto;
    }

    @Override
    public UserInfoVo getUserInfo(SysUser sysUser) {
        UserInfoVo vo = new UserInfoVo();
        vo.setSysUser(sysUser);
        // 获取所有sysRole的id
        List<Integer> roleIds = sysRoleMapper.listRoleByUserId(sysUser.getUserId()).stream()
                .map(SysRole::getRoleId).collect(Collectors.toList());
        // 获取角色所对应的菜单访问权限
        String[] permissions = roleIds.stream().map(sysMenuService::loadMenuByRoleId).flatMap(Collection::stream)
                .filter(menu -> MenuTypeEnum.BUTTON.getType().equals(menu.getType())).map(SysMenu::getPermission)
                .filter(StrUtil::isNotBlank).toArray(String[]::new);
        vo.setPermissions(permissions);
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveUser(UserDTO userDTO) {
        SysUser sysUser = new SysUser();
        BeanUtil.copyProperties(userDTO, sysUser);
        sysUser.setPassword(ENCODER.encode(sysUser.getPassword()));
        baseMapper.insert(sysUser);
        userDTO.getRole().stream().map(id->{
            SysUserRole userRole = new SysUserRole();
            userRole.setRoleId(id);
            userRole.setUserId(sysUser.getUserId());
            return userRole;
        }).forEach(userRoleMapper::insert);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = CacheConstants.USER_DETAILS, key = "#user.username")
    public boolean deleteUser(SysUser user) {
        Integer userId = user.getUserId();
        userMapper.deleteById(userId);
        userRoleMapper.removeUserRoleByUserID(userId);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheConstants.USER_DETAILS, key = "#userDTO.username")
    public boolean updateUser(UserDTO userDTO) {
        SysUser user = new SysUser();
        BeanUtil.copyProperties(userDTO, user);
        if (StrUtil.isNotBlank(userDTO.getPassword())) {
            user.setPassword(ENCODER.encode(userDTO.getPassword()));
        }
        userMapper.update(user, Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUserId, userDTO.getUserId()));
        // 同时更新user与role的关系
        userRoleMapper.removeUserRoleByUserID(userDTO.getUserId());
        List<Integer> roles = userDTO.getRole();
        roles.forEach(role->{
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(userDTO.getUserId());
            userRole.setRoleId(role);
            userRoleMapper.insert(userRole);
        });
        return true;
    }

    @Override
    @CacheEvict(value = CacheConstants.USER_DETAILS, key = "#userDTO.username")
    public boolean updateCurrUserInfo(UserDTO userDTO) {
        SysUser user = userMapper.selectOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, userDTO.getUsername()));
        Assert.isTrue(ENCODER.matches(userDTO.getPassword(), user.getPassword()), "密码不正确，信息修改失败");
        SysUser newUser = new SysUser();
        if (StrUtil.isNotBlank(userDTO.getNewpassword1())) {
            newUser.setPassword(ENCODER.encode(userDTO.getNewpassword1()));
        }
        newUser.setPhone(userDTO.getPhone());
        newUser.setUserId(user.getUserId());
        newUser.setAvatar(userDTO.getAvatar());
        return this.updateById(newUser);
    }
}
