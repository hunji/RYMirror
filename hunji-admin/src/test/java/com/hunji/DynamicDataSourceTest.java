package com.hunji;

import com.hunji.system.domain.SysUser;
import com.hunji.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

/**
 * 多数据源测试类
 *
 * @author hunji
 * @version 1.0
 * @date 2023/1/6 11:46
 */
@Slf4j
@SpringBootTest(classes =HunjiApplication.class)
public class DynamicDataSourceTest {
    @Autowired
    private ISysUserService userService;

    @Test
    public void sysUserTest(){
        userService.getAllUsersMaster().forEach(System.out::println);
        userService.getAllUsersSlave().forEach(System.out::println);
    }
}
