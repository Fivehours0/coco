package com.cococloud.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cococloud.upms.common.dto.SysLogDTO;
import com.cococloud.upms.common.entity.SysLog;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * 日志表 服务类
 * </p>
 *
 * @author dzh
 * @since 2021-12-31
 */
public interface SysLogService extends IService<SysLog> {
    IPage<SysLog> selectLogPage(Page<SysLog> page, SysLogDTO logDTO);
}
