package com.cococloud.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cococloud.upms.common.entity.SysDept;
import com.cococloud.upms.common.entity.SysDeptRelation;
import com.cococloud.admin.mapper.SysDeptRelationMapper;
import com.cococloud.admin.service.SysDeptRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 部门关系表 服务实现类
 * </p>
 *
 * @author dzh
 * @since 2021-12-31
 */
@Service
@AllArgsConstructor
public class SysDeptRelationServiceImpl extends ServiceImpl<SysDeptRelationMapper, SysDeptRelation> implements SysDeptRelationService {

    private final SysDeptRelationMapper deptRelationMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveDeptRelation(SysDept sysDept) {
        List<SysDeptRelation> relations = deptRelationMapper.selectList(
                Wrappers.<SysDeptRelation>lambdaQuery().eq(SysDeptRelation::getDescendant, sysDept.getParentId()))
                .stream().peek(relation -> relation.setDescendant(sysDept.getDeptId())).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(relations)) {
            this.saveBatch(relations);
        }
        // 维护自己
        SysDeptRelation deptRelation = new SysDeptRelation();
        deptRelation.setAncestor(sysDept.getDeptId());
        deptRelation.setDescendant(sysDept.getDeptId());
        return this.save(deptRelation);
    }

    /**
     * 更新部门关系
     */
    @Override
    public void updateDeptRelation(SysDeptRelation relation) {
        deptRelationMapper.updateDeptRelation(relation);
        deptRelationMapper.insertRelationWhenUpdate(relation);
    }

    @Override
    public boolean removeDeptRelationById(Integer id) {
        return deptRelationMapper.removeDeptRelationById(id);
    }
}
