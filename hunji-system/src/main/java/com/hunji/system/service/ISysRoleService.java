package com.hunji.system.service;

import java.util.Set;

/**
 * @author hunji
 * @date 2023/7/28 10:49
 */
public interface ISysRoleService {
    /**
     * 根据用户ID查询角色权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    public Set<String> selectRolePermissionByUserId(Long userId);
}
