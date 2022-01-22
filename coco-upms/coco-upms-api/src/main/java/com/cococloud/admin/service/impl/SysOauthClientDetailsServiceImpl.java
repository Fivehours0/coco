package com.cococloud.admin.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cococloud.admin.mapper.SysLogMapper;
import com.cococloud.upms.common.dto.SysLogDTO;
import com.cococloud.upms.common.entity.SysLog;
import com.cococloud.upms.common.entity.SysOauthClientDetails;
import com.cococloud.admin.mapper.SysOauthClientDetailsMapper;
import com.cococloud.admin.service.SysOauthClientDetailsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 终端信息表 服务实现类
 * </p>
 *
 * @author dzh
 * @since 2021-12-31
 */
@Service
@AllArgsConstructor
public class SysOauthClientDetailsServiceImpl extends ServiceImpl<SysOauthClientDetailsMapper, SysOauthClientDetails> implements SysOauthClientDetailsService {

    private final SysOauthClientDetailsMapper clientDetailsMapper;

    @Override
    public IPage<SysOauthClientDetails> selectClientPage(Page<SysOauthClientDetails> page) {
        LambdaQueryWrapper<SysOauthClientDetails> wrapper = new LambdaQueryWrapper<>();
        return clientDetailsMapper.selectPage(page, wrapper);
    }
}
