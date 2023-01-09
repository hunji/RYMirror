package com.hunji.system.mapper;

import com.hunji.system.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * @author hunji
 * @version 1.0
 * @date 2023/1/6 11:16
 */
@Mapper
public interface SysUserMapper {
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
