package com.hunji.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.hunji.system.mapper.SysRoleMapper;
import com.hunji.system.service.ISysRoleService;

import com.hunji.common.core.domain.entity.SysRole;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 角色 业务层处理
 *
 * @author hunji
 * @version 1.0
 * @date 2023/7/28 10:51
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService {
    private SysRoleMapper roleMapper;

    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        List<SysRole> perms = roleMapper.selectRolePermissionByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : perms) {
            if(perm!=null){
                permsSet.addAll((Arrays.asList(perm.getRoleKey().trim().split(","))));
            }
        }
        return permsSet;
    }
}
