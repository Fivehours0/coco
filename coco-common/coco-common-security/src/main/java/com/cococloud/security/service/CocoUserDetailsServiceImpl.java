package com.cococloud.security.service;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;

import com.cococloud.common.constant.CacheConstants;
import com.cococloud.security.component.CocoUser;
import com.cococloud.common.constant.CommonConstans;
import com.cococloud.common.constant.SecurityConstants;
import com.cococloud.common.util.CommentResult;
import com.cococloud.upms.common.dto.UserDetailsDto;
import com.cococloud.upms.common.entity.SysUser;
import com.cococloud.upms.common.feign.RemoteUserDetail;
import lombok.AllArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class CocoUserDetailsServiceImpl implements UserDetailsService {

    private final RemoteUserDetail remoteUserDetail;

    private final CacheManager cacheManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);

        if (cache != null && cache.get(username) != null) {
            return (UserDetails) (cache.get(username).get());
        }

        CommentResult<UserDetailsDto> userDetailsDto = remoteUserDetail.loadUser(username, SecurityConstants.INNER_VALUE);
        if (userDetailsDto.getData() == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        UserDetailsDto userInfo = userDetailsDto.getData();
        SysUser sysUser = userInfo.getSysUser();

        ArrayList<String> authorities = new ArrayList<>();
        if (ArrayUtil.isNotEmpty(userInfo.getRoleId())){
            userInfo.getRoleId().forEach(id->authorities.add(SecurityConstants.ROLE_PREFIX+id));
            authorities.addAll(Arrays.asList(userInfo.getPermission()));
        }
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(authorities.toArray(new String[0]));

        CocoUser cocoUser = new CocoUser(sysUser.getUserId(), sysUser.getUsername(),
                SecurityConstants.POSSWORD_ENCODING + sysUser.getPassword(),
                StrUtil.equals(sysUser.getLockFlag(), CommonConstans.STATUS_NORMAL),
                true,
                true,
                StrUtil.equals(sysUser.getLockFlag(), CommonConstans.STATUS_NORMAL),
                authorityList);

        if (cache != null) {
            cache.put(username, cocoUser);
        }
        return cocoUser;
    }
}
