package com.hunji.framework.config;

import com.hunji.framework.interceptor.RepeatSubmitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * TODO
 *
 * @author hunji
 * @version 1.0
 * @date 2023/1/13 17:04
 */
@Configuration
public class ResourcesConfig implements WebMvcConfigurer {

    private final RepeatSubmitInterceptor repeatSubmitInterceptor;

    public ResourcesConfig(RepeatSubmitInterceptor repeatSubmitInterceptor) {
        this.repeatSubmitInterceptor = repeatSubmitInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(repeatSubmitInterceptor).addPathPatterns("/**");
    }
}
