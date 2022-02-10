package com.cococloud.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cococloud.admin.mapper.SysUserMapper;
import com.cococloud.log.annotation.SysLog;
import com.cococloud.security.annotation.SecurityIgnoreUrl;
import com.cococloud.upms.common.dto.UserDTO;
import com.cococloud.upms.common.dto.UserDetailsDto;
import com.cococloud.upms.common.entity.SysUser;
import com.cococloud.admin.service.SysUserService;
import com.cococloud.common.util.CommentResult;
import com.cococloud.upms.common.vo.UserInfoVo;
import com.cococloud.upms.common.vo.UserVO;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author dzh
 * @since 2021-12-31
 */
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class SysUserController {

    private final SysUserService userService;

    private final SysUserMapper userMapper;

    /**
     * 根据username从数据库表中查询SysUser, 并根据SysUser, 构建并返回一个UserDetailsDto
     */
    @SecurityIgnoreUrl
    @GetMapping("/info/{username}")
    public CommentResult<UserDetailsDto> loadUser(@PathVariable("username") String username) {
        QueryWrapper<SysUser> qw = new QueryWrapper<SysUser>().eq("username", username);
        SysUser user = userService.getOne(qw);
        if (user == null) {
            return CommentResult.failed(String.format("用户名不存在 %s", username));
        }
        return CommentResult.ok(userService.getUserDetails(user));
    }

    /**
     * 获取访问用户的详细信息
     * 访问用户已经过授权服务器的认证
     */
    @GetMapping("/info")
    public CommentResult<UserInfoVo> loadUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String username = null;
        if (principal instanceof User) {
            username = ((User) principal).getUsername();
        }
        QueryWrapper<SysUser> qw = new QueryWrapper<SysUser>().eq("username", username);
        SysUser user = userService.getOne(qw);
        if (user == null) {
            return CommentResult.failed("获取用户信息失败");
        }
        UserInfoVo userInfo = userService.getUserInfo(user);
        return CommentResult.ok(userInfo);
    }

    /**
     * 构建用户管理页面
     */
    @GetMapping("/page")
    public CommentResult<IPage<UserVO>> selectUserPageVo(Page<?> page, UserDTO userDTO) {
        return CommentResult.ok(userMapper.selectUserPageVo(page, userDTO));
    }

    /**
     * 添加用户时，输入用户名会触发事件，获取信息来判断是否存在该用户
     */
    @GetMapping("/details/{username}")
    public CommentResult<SysUser> selectUserByUsername(@PathVariable("username") String username) {
        return CommentResult.ok(userService.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username)));
    }

    /**
     * 添加用户
     */
    @SysLog("添加用户")
    @PreAuthorize("hasAuthority('sys_user_add')")
    @PostMapping
    public CommentResult<Boolean> insertUserVo(@RequestBody UserDTO userDTO) {
        return CommentResult.ok(userService.saveUser(userDTO));
    }

    /**
     * 删除用户
     */
    @SysLog("删除用户")
    @PreAuthorize("hasAuthority('sys_user_del')")
    @DeleteMapping("/{id}")
    public CommentResult<Boolean> deleteUser(@PathVariable("id") Integer id) {
        SysUser sysUser = userService.getById(id);
        return CommentResult.ok(userService.deleteUser(sysUser));
    }

    /**
     * 更新用户信息
     */
    @SysLog("更新用户")
    @PreAuthorize("hasAuthority('sys_user_edit')")
    @PutMapping
    public CommentResult<Boolean> updateUser(@RequestBody UserDTO userDTO) {
        return CommentResult.ok(userService.updateUser(userDTO));
    }

    /**
     * 编辑当前登录用户的信息
     */
    @SysLog("编辑个人信息")
    @PutMapping("/edit")
    public CommentResult<Boolean> updateCurrUserInfo(@RequestBody UserDTO userDTO) {
        return CommentResult.ok(userService.updateCurrUserInfo(userDTO));
    }

}

