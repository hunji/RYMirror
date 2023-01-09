package com.hunji.system.service;

import com.hunji.system.domain.SysUser;

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
}
