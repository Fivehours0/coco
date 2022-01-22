package com.cococloud.admin.service;

import com.cococloud.upms.common.entity.SysDept;
import com.cococloud.upms.common.entity.SysDeptRelation;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 部门关系表 服务类
 * </p>
 *
 * @author dzh
 * @since 2021-12-31
 */
public interface SysDeptRelationService extends IService<SysDeptRelation> {
    /**
     * 新建部门关系
     * @param sysDept 部门实体类
     */
    boolean saveDeptRelation(SysDept sysDept);

    /**
     * 移除该部门id以及该部门下的所有子部门
     * @param id 需要移除的部门id
     * @return 是否操作成功
     */
    boolean removeDeptRelationById(Integer id);

    /**
     * 更新部门关系
     */
    void updateDeptRelation(SysDeptRelation relation);
}
