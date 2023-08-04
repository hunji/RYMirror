package com.hunji.system.service.impl;

import com.hunji.common.annotation.DataScope;
import com.hunji.common.core.domain.entity.SysUser;
import com.hunji.common.enums.DataSourceType;
import com.hunji.common.annotation.DataSource;
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
    private final SysUserMapper mapper;
    @Autowired
    public SysUserServiceImpl(SysUserMapper mapper) {
        this.mapper = mapper;
    }

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

    @Override
    public SysUser selectUserByUserName(String userName) {
        return mapper.selectUserByUserName(userName);
    }

    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUserList(SysUser user)
    {
        return mapper.selectUserList(user);
    }
}
