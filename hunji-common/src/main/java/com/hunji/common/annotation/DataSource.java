package com.hunji.common.annotation;

import com.hunji.common.enums.DataSourceType;

import java.lang.annotation.*;

/**
 * 多数据源注解
 *
 * 优先级：先方法，后类
 *
 * @author hunji
 * @version 1.0
 * @date 2022/12/20 15:31
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource
{
    /**
     * 切换数据源名称
     */
    DataSourceType value() default DataSourceType.MASTER;
}
