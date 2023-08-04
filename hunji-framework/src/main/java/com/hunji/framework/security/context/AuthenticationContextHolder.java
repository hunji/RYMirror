package com.hunji.framework.security.context;

import org.springframework.security.core.Authentication;

/**
 * 身份验证信息 封装类
 *
 * @author hunji
 * @version 1.0
 * @date 2023/7/27 16:15
 */
public class AuthenticationContextHolder {
    private static final ThreadLocal<Authentication> contextHolder = new ThreadLocal<>();

    public static Authentication getContext() {
        return contextHolder.get();
    }

    public static void setContext(Authentication context) {
        contextHolder.set(context);
    }

    public static void clearContext() {
        contextHolder.remove();
    }
}
