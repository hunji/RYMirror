package com.hunji.common.annotation;

import java.lang.annotation.*;

/**
 * 匿名访问注解
 * @author hunji
 * @date 2023/7/7 15:14
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Anonymous {
}
