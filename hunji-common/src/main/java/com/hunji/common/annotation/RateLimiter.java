package com.hunji.common.annotation;

import com.hunji.common.constant.CacheConstants;
import com.hunji.common.enums.LimitType;

import java.lang.annotation.*;

/**
 * 限流注解
 * @author hunji
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {
    /**
     * 限流key
     * @return
     */
    String key() default CacheConstants.RATE_LIMIT_KEY;

    int time() default 60;

    int count() default 100;

    LimitType limitType() default LimitType.DEFAULT;
}
