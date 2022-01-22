package com.cococloud.admin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cococloud.admin.service.SysDictItemService;
import com.cococloud.admin.service.SysDictService;
import com.cococloud.common.util.CommentResult;
import com.cococloud.upms.common.entity.SysDict;
import com.cococloud.upms.common.entity.SysDictItem;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author dzh
 * @since 2021-12-31
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dict")
public class SysDictController {

    private final SysDictItemService dictItemService;

    private final SysDictService dictService;

    /**
     * 通过字典类型查找字典
     * @param type 字典类型
     * @return 字典
     */
    @GetMapping("/type/{type}")
    public CommentResult<List<SysDictItem>> getDictByType(@PathVariable String type) {
        return CommentResult.ok(dictItemService.list(Wrappers.<SysDictItem>query().lambda().eq(SysDictItem::getType, type)));
    }

    /**
     * 字典管理页面获取字典信息
     */
    @GetMapping("/page")
    public CommentResult<IPage<SysDict>> selectDictPage(Page<SysDict> page, SysDict dict) {
        return CommentResult.ok(dictService.page(page, Wrappers.query(dict)));
    }
}

