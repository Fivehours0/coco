package com.cococloud.admin.service;

import cn.hutool.core.lang.tree.Tree;
import com.cococloud.upms.common.entity.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 部门管理 服务类
 * </p>
 *
 * @author dzh
 * @since 2021-12-31
 */
public interface SysDeptService extends IService<SysDept> {

    /**
     * 构建部门管理页面
     */
    List<Tree<Integer>> buildDeptTree();

    /**
     * 根据dept id删除部门信息
     */
    boolean removeDeptById(Integer id);

    /**
     * 更新部门信息
     */
    boolean updateDeptById(SysDept dept);

    /**
     * 新增部门
     */
    boolean saveDept(SysDept dept);
}
