package com.hunji.web.controller;

import com.hunji.common.annotation.RateLimiter;
import com.hunji.common.enums.LimitType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试类
 *
 * @author hunji
 * @version 1.0
 * @date 2023/1/9 10:04
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    /**
     * 限流，100 秒之内，这个接口可以访问 3 次;expire操作单位是秒
     */
    @RateLimiter(time = 100, count = 3, limitType = LimitType.IP)
    public String hello() {
        return "hello";
    }
}
