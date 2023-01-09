package com.hunji.common.enums;

/**
 * 限流类型
 * @author hunji
 */
public enum LimitType {
    /**
     * 默认策略全局限流
     */
    DEFAULT,
    /**
     * 根据请求IP限流
     */
    IP
}
