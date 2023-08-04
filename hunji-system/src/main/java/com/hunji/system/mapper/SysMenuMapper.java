package com.hunji.system.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜单表 数据层
 *
 * @author hunji
 * @version 1.0
 * @date 2023/7/28 11:46
 */
@Mapper
public interface SysMenuMapper {
    /**
     * 根据角色查看权限信息
     * @param roleId 角色id
     * @return 权限内容
     */
    List<String> selectMenuPermsByRoleId(Long roleId);

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    List<String> selectMenuPermsByUserId(Long userId);
}
