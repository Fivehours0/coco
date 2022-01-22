package com.cococloud.admin.mapper;

import com.cococloud.upms.common.entity.SysDept;
import com.cococloud.upms.common.entity.SysDeptRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 部门关系表 Mapper 接口
 * </p>
 *
 * @author dzh
 * @since 2021-12-31
 */
@Mapper
public interface SysDeptRelationMapper extends BaseMapper<SysDeptRelation> {
    /**
     * 移除该部门id以及该部门下的所有子部门
     * @param id 需要移除的部门id
     * @return 是否操作成功
     */
    boolean removeDeptRelationById(@Param("id") Integer id);

    /**
     * 更新部门关系
     */
    boolean updateDeptRelation(SysDeptRelation deptRelation);

    /**
     * 更新部门关系时，插入更新的新关系
     */
    boolean insertRelationWhenUpdate(SysDeptRelation deptRelation);
}
