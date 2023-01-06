package com.hunji.system.service;

import com.hunji.system.domain.SysUser;

import java.util.List;

public interface ISysUserService {
    List<SysUser> getAllUsersMaster();
    List<SysUser> getAllUsersSlave();
}
