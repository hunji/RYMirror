package com.hunji.system.service;

import java.util.Set;

/**
 * 菜单 业务层
 * 目录/菜单/按钮 可以看做权限数据
 * @author hunji
 * @date 2023/7/28 11:44
 */
public interface ISysMenuService {
    /**
     * 根据角色ID查询权限
     *
     * @param roleId 角色ID
     * @return 权限列表
     */
    Set<String> selectMenuPermsByRoleId(Long roleId);
    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> selectMenuPermsByUserId(Long userId);
}
