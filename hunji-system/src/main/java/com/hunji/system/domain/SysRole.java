package com.hunji.system.domain;

import com.hunji.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Set;

/**
 * 角色
 *
 * @author hunji
 * @version 1.0
 * @date 2023/1/31 10:13
 */
@Data
public class SysRole extends BaseEntity {
    private Long roleId;
    private String roleName;
    private String roleKey;
    private String roleSort;
    private String dataScope;
    private boolean menuCheckStrictly;
    private boolean deptCheckStrictly;
    private String status;
    private String delFlag;
    private boolean flag = false;
    /** 菜单组 */
    private Long[] menuIds;

    /** 部门组（数据权限） */
    private Long[] deptIds;

    /** 角色菜单权限 */
    private Set<String> permissions;
}
