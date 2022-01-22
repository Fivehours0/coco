package com.cococloud.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cococloud.upms.common.dto.SysLogDTO;
import com.cococloud.upms.common.entity.SysLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 日志表 Mapper 接口
 * </p>
 *
 * @author dzh
 * @since 2021-12-31
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {
}
