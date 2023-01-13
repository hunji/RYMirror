package com.hunji.common.annotation;

import java.lang.annotation.*;

/**
 * 防止表单重复提交注解
 * @author hunji
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RepeatSubmit {
    /**
     * 间隔时间(ms)
     * @return
     */
    int interval() default 5000;

    /**
     * 提示消息
     */
    String message() default "不允许重复提交，请稍候再试";


}
