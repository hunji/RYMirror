package com.hunji.system.service.impl;

import com.hunji.common.DataSourceType;
import com.hunji.common.annotation.DataSource;
import com.hunji.system.domain.SysUser;
import com.hunji.system.mapper.SysUserMapper;
import com.hunji.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author hunji
 * @version 1.0
 * @date 2023/1/6 11:43
 */
@Service
public class SysUserServiceImpl implements ISysUserService {
    @Autowired
    private SysUserMapper mapper;

    @Override
    @DataSource(DataSourceType.MASTER)
    public List<SysUser> getAllUsersMaster() {
        return mapper.getAllUsersMaster();
    }

    @Override
    @DataSource(DataSourceType.SLAVE)
    public List<SysUser> getAllUsersSlave() {
        return mapper.getAllUsersSlave();
    }
}
