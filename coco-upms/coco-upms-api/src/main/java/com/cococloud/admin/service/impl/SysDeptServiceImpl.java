package com.cococloud.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cococloud.admin.mapper.SysDeptRelationMapper;
import com.cococloud.admin.service.SysDeptRelationService;
import com.cococloud.upms.common.entity.SysDept;
import com.cococloud.admin.mapper.SysDeptMapper;
import com.cococloud.admin.service.SysDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cococloud.upms.common.entity.SysDeptRelation;
import com.cococloud.upms.common.entity.SysMenu;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.sql.Wrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 部门管理 服务实现类
 * </p>
 *
 * @author dzh
 * @since 2021-12-31
 */
@Service
@AllArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    private final SysDeptMapper deptMapper;

    private final SysDeptRelationService deptRelationService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeDeptById(Integer id) {
        // 获取该部门的子部门
        List<Integer> ids = deptRelationService.list(
                Wrappers.<SysDeptRelation>lambdaQuery().eq(SysDeptRelation::getAncestor, id))
                .stream().map(SysDeptRelation::getDescendant)
                .collect(Collectors.toList());
        // 删除该部门下的所有子部门
        if (CollUtil.isNotEmpty(ids)) {
            this.removeByIds(ids);
        }
        // 移除部门间的关系
        deptRelationService.removeDeptRelationById(id);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDeptById(SysDept dept) {
        Assert.isTrue(!dept.getDeptId().equals(dept.getParentId()), "不能将自己设置为父级菜单");
        this.updateById(dept);
        SysDeptRelation deptRelation = new SysDeptRelation();
        deptRelation.setAncestor(dept.getParentId());
        deptRelation.setDescendant(dept.getDeptId());
        deptRelationService.updateDeptRelation(deptRelation);
        return true;
    }

    @Override
    public List<Tree<Integer>> buildDeptTree() {
        List<SysDept> depts = deptMapper.selectList(Wrappers.<SysDept>lambdaQuery().orderByAsc(SysDept::getSort));
        List<TreeNode<Integer>> nodes = depts.stream().map(getNodeFunction()).collect(Collectors.toList());
        return TreeUtil.build(nodes);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveDept(SysDept dept) {
        this.save(dept);
        deptRelationService.saveDeptRelation(dept);
        return true;
    }

    private Function<SysDept, TreeNode<Integer>> getNodeFunction() {
        return dept -> {
            TreeNode<Integer> node = new TreeNode<>();
            node.setId(dept.getDeptId());
            node.setName(dept.getName());
            node.setParentId(dept.getParentId());
            node.setWeight(dept.getSort());
            // 扩展属性
            Map<String, Object> extra = new HashMap<>(4);
            extra.put("createBy", dept.getCreateBy());
            extra.put("createTime", dept.getCreateTime());
            node.setExtra(extra);
            return node;
        };
    }
}
