package com.hunji.framework.aspectj;

import cn.hutool.core.util.StrUtil;
import com.hunji.common.annotation.DataScope;
import com.hunji.common.core.domain.BaseEntity;
import com.hunji.system.domain.SysRole;
import com.hunji.system.domain.SysUser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据过滤处理
 *
 * @author hunji
 * @version 1.0
 * @date 2023/1/19 12:20
 */
@Aspect
@Component
public class DataScopeAspect {
    /**
     * 全部数据权限
     */
    public static final String DATA_SCOPE_ALL = "1";

    /**
     * 自定数据权限
     */
    public static final String DATA_SCOPE_CUSTOM = "2";

    /**
     * 部门数据权限
     */
    public static final String DATA_SCOPE_DEPT = "3";

    /**
     * 部门及以下数据权限
     */
    public static final String DATA_SCOPE_DEPT_AND_CHILD = "4";

    /**
     * 仅本人数据权限
     */
    public static final String DATA_SCOPE_SELF = "5";

    public static final String DATA_SCOPE = "dataScope";

    @Before("@annotation(controllerDataScope)")
    public void doBefore(JoinPoint point, DataScope controllerDataScope) {
        clearDataScope(point);
        handleDataScope(point, controllerDataScope);
    }

    private void handleDataScope(JoinPoint point, DataScope controllerDataScope) {
        // 获取用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SysUser currentUser = (SysUser) authentication.getPrincipal();

        if(currentUser!=null && !currentUser.isAdmin()){
            dataScopeFilter(point, currentUser, controllerDataScope.deptAlias(),
                    controllerDataScope.userAlias());
        }
    }

    private void dataScopeFilter(JoinPoint point, SysUser currentUser, String deptAlias, String userAlias) {
        StringBuilder sqlString = new StringBuilder();
        // 考虑多个角色的数据权限可能重复的情况
        List<String> conditions = new ArrayList<String>();

        for (SysRole role : currentUser.getRoles())
        {
            String dataScope = role.getDataScope();
            if (!DATA_SCOPE_CUSTOM.equals(dataScope) && conditions.contains(dataScope))
            {
                continue;
            }
            if (DATA_SCOPE_ALL.equals(dataScope))
            {
                sqlString = new StringBuilder();
                break;
            }
            else if (DATA_SCOPE_CUSTOM.equals(dataScope))
            {
                sqlString.append(StrUtil.format(
                        " OR {}.dept_id IN ( SELECT dept_id FROM sys_role_dept WHERE role_id = {} ) ", deptAlias,
                        role.getRoleId()));
            }
            else if (DATA_SCOPE_DEPT.equals(dataScope))
            {
                sqlString.append(StrUtil.format(" OR {}.dept_id = {} ", deptAlias, currentUser.getDeptId()));
            }
            else if (DATA_SCOPE_DEPT_AND_CHILD.equals(dataScope))
            {
                sqlString.append(StrUtil.format(
                        " OR {}.dept_id IN ( SELECT dept_id FROM sys_dept WHERE dept_id = {} or find_in_set( {} , ancestors ) )",
                        deptAlias, currentUser.getDeptId(), currentUser.getDeptId()));
            }
            else if (DATA_SCOPE_SELF.equals(dataScope))
            {
                if (StrUtil.isNotBlank(userAlias))
                {
                    sqlString.append(StrUtil.format(" OR {}.user_id = {} ", userAlias, currentUser.getUserId()));
                }
                else
                {
                    // 数据权限为仅本人且没有userAlias别名不查询任何数据
                    sqlString.append(StrUtil.format(" OR {}.dept_id = 0 ", deptAlias));
                }
            }
            conditions.add(dataScope);
        }

        if (StrUtil.isNotBlank(sqlString.toString()))
        {
            Object params = point.getArgs()[0];
            if (params!=null && params instanceof BaseEntity)
            {
                BaseEntity baseEntity = (BaseEntity) params;
                baseEntity.getParams().put(DATA_SCOPE, " AND (" + sqlString.substring(4) + ")");
            }
        }

    }

    /**
     * 因为这个参数是后台拼接出来的，在接受到参数的时候先清除了由后台生成
     * 避免参数注入
     *
     * @param point
     */
    private void clearDataScope(final JoinPoint point) {
        Object params = point.getArgs()[0];
        if (params != null && params instanceof BaseEntity) {
            BaseEntity base = (BaseEntity) params;
            base.getParams().put(DATA_SCOPE,"");
        }
    }
}
