package com.hunji.system.service;
import com.hunji.common.core.domain.entity.SysUser;

import java.util.List;

/**
 * @author hunji
 */
public interface ISysUserService {
    /**
     * 测试方法，获取master数据源数据
     * @return
     */
    List<SysUser> getAllUsersMaster();

    /**
     * 测试方法，获取slave数据源数据
     * @return
     */
    List<SysUser> getAllUsersSlave();

    /**
     *
     * @param userName
     * @return 人员信息
     */
    SysUser selectUserByUserName(String userName);
    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    List<SysUser> selectUserList(SysUser user);
}
