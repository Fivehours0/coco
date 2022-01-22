package com.cococloud.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cococloud.upms.common.entity.SysOauthClientDetails;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 终端信息表 服务类
 * </p>
 *
 * @author dzh
 * @since 2021-12-31
 */
public interface SysOauthClientDetailsService extends IService<SysOauthClientDetails> {
    /**
     * 分页获取client信息
     */
    IPage<SysOauthClientDetails> selectClientPage(Page<SysOauthClientDetails> page);
}
