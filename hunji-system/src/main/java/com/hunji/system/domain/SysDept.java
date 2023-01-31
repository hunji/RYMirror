package com.hunji.system.domain;

import com.hunji.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门
 *
 * @author hunji
 * @version 1.0
 * @date 2023/1/31 10:13
 */
@Data
public class SysDept extends BaseEntity {
    /** 部门ID */
    private Long deptId;

    /** 父部门ID */
    private Long parentId;

    /** 祖级列表 */
    private String ancestors;

    /** 部门名称 */
    private String deptName;

    /** 显示顺序 */
    private Integer orderNum;

    /** 负责人 */
    private String leader;

    /** 联系电话 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 部门状态:0正常,1停用 */
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 父部门名称 */
    private String parentName;

    /** 子部门 */
    private List<SysDept> children = new ArrayList<SysDept>();
}
