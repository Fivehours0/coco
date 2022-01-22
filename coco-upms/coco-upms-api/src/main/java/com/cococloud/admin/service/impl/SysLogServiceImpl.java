package com.cococloud.admin.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cococloud.upms.common.dto.SysLogDTO;
import com.cococloud.upms.common.entity.SysLog;
import com.cococloud.admin.mapper.SysLogMapper;
import com.cococloud.admin.service.SysLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 日志表 服务实现类
 * </p>
 *
 * @author dzh
 * @since 2021-12-31
 */
@Service
@AllArgsConstructor
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    private final SysLogMapper logMapper;

    @Override
    public IPage<SysLog> selectLogPage(Page<SysLog> page, SysLogDTO logDTO) {
        LambdaQueryWrapper<SysLog> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(logDTO.getType())) {
            wrapper = wrapper.eq(SysLog::getType, logDTO.getType());
        }
        if (ArrayUtil.isNotEmpty(logDTO.getCreateTime())) {
            wrapper = wrapper.ge(SysLog::getCreateTime, logDTO.getCreateTime()[0])
                    .le(SysLog::getCreateTime, logDTO.getCreateTime()[1]);
        }
        return logMapper.selectPage(page, wrapper);
    }
}
