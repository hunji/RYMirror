package com.hunji.framework.config;

import com.hunji.framework.interceptor.impl.SameUrlDataInterceptor;
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

    private final SameUrlDataInterceptor repeatSubmitInterceptor;

    public ResourcesConfig(SameUrlDataInterceptor repeatSubmitInterceptor) {
        this.repeatSubmitInterceptor = repeatSubmitInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(repeatSubmitInterceptor).addPathPatterns("/**");
    }
}
