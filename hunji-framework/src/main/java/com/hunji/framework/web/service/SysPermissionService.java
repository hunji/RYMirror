package com.hunji.framework.web.service;

import com.hunji.common.core.domain.entity.SysRole;
import com.hunji.common.core.domain.entity.SysUser;
import com.hunji.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户权限处理
 *
 * @author hunji
 * @version 1.0
 * @date 2023/7/28 11:36
 */
@Component
public class SysPermissionService {
    @Autowired
    private ISysMenuService menuService;
    /**
     * 获取菜单权限
     * @param user  用户信息
     * @return  菜单权限
     */
    public Set<String> getMenuPermission(SysUser user){
        // 使用set集合，相当于找出权限的并集
        Set<String> perms = new HashSet<>();
        //  管理员
        if(user.isAdmin()){
            perms.add("*:*:*");
        }else{
            List<SysRole> roles = user.getRoles();
            // 这里可以进行扩展，比如说 直接给用户授权而不是给角色
            if(!roles.isEmpty() && roles.size()>1){
                for (SysRole role : roles) {
                    Set<String> rolePerms = menuService.selectMenuPermsByRoleId(role.getRoleId());
                    role.setPermissions(rolePerms);
                    perms.addAll(rolePerms);
                }
            }else{
                perms.addAll(menuService.selectMenuPermsByUserId(user.getUserId()));
            }
        }
        return perms;
    }
}
