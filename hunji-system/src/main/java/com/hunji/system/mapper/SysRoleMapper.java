package com.hunji.system.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.hunji.common.core.domain.entity.SysRole;

import java.util.List;

/**
 * @author hunji
 * @date 2023/7/28 11:10
 */
@Mapper
public interface SysRoleMapper {
    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRole> selectRolePermissionByUserId(Long userId);
}
