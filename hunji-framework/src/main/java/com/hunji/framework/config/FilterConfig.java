package com.hunji.framework.config;

import com.hunji.common.filter.RepeatableFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 配置过滤器
 *
 * @author hunji
 * @version 1.0
 * @date 2023/1/13 10:51
 */
@Component
public class FilterConfig {
    @Bean
    public FilterRegistrationBean repeatableFilterRegistration(){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new RepeatableFilter());
        registration.addUrlPatterns("/*");
        registration.setName("repeatableFilter");
        registration.setOrder(FilterRegistrationBean.LOWEST_PRECEDENCE);
        return registration;
    }
}
