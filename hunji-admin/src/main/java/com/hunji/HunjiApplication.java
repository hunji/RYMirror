package com.hunji;

import cn.hutool.extra.spring.SpringUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动类
 *
 * @author hunji
 * @version 1.0
 * @date 2023/1/6 11:37
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@MapperScan("com.hunji.**.mapper")
public class HunjiApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(HunjiApplication.class, args);
    }
}
