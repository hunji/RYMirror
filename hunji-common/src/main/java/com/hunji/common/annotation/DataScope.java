package com.hunji.common.annotation;

import java.lang.annotation.*;

/**
 * 数据权限注解
 * @author hunji
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {
    String deptAlias() default "";

    String userAlias() default "";

    /**
     * 权限字符串
     * @return
     */
    String permission() default "";
}
