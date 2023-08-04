package com.hunji.web.controller;

import com.hunji.common.annotation.Anonymous;
import com.hunji.common.annotation.RateLimiter;
import com.hunji.common.annotation.RepeatSubmit;
import com.hunji.common.enums.LimitType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试类
 *
 * @author hunji
 * @version 1.0
 * @date 2023/1/9 10:04
 */
@RestController
@Anonymous
public class HelloController {
    @GetMapping("/hello")
    /**
     * 限流，100 秒之内，这个接口可以访问 3 次;expire操作单位是秒
     */
    @RateLimiter(time = 100, count = 3, limitType = LimitType.IP)
    public String hello() {
        return "hello";
    }

    /**
     * 测试： 在apipost中发送post请求：http://localhost:8080/repeatTest
     *        在请求头中添加参数Authorization: Bearer:123213123
     */
    @PostMapping("/repeatTest")
    @RepeatSubmit(interval = 100000, message = "重复提交提示！！！")
    public String repeatTest(@RequestBody String json) {
        return json;
    }

    @PostMapping("/repeatTest2")
    @RepeatSubmit(interval = 100000, message = "重复提交提示！！！")
    public String repeatTest2(@RequestBody String json) {
        return json;
    }
}
