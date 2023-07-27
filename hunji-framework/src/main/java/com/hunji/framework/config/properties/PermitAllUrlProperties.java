package com.hunji.framework.config.properties;

import com.hunji.common.annotation.Anonymous;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.apache.commons.lang3.RegExUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * 注解允许匿名访问的url集合
 *
 * @author hunji
 * @version 1.0
 * @date 2023/7/7 15:17
 */
@Configuration
public class PermitAllUrlProperties implements InitializingBean, ApplicationContextAware {
    /**
     * 正则表达式 匹配path variable
     * 如： @GetMapping(value = "/configKey/{configKey}") 进行匹配后替换为/configKey/*
     *
     */
    private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");

    /**
     * spring 上下文
     * 从中可以获取到各种bean
     */
    private ApplicationContext applicationContext;
    /**
     * 该集合中保存了全部标记过匿名注解的url请求
     */
    private List<String> urls = new ArrayList<>();

    public String ASTERISK = "*";

    @Override
    public void afterPropertiesSet()  {
        // 获取全部的handlerMappings
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

        map.keySet().forEach(info->{
            HandlerMethod handlerMethod = map.get(info);
            // 获取方法上的注解 替代path variable 为 *
            Anonymous method = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), Anonymous.class);
            Optional.ofNullable(method).ifPresent(anonymous->info.getPatternsCondition().getPatterns()
                    .forEach(url->urls.add(RegExUtils.replaceAll(url,PATTERN,ASTERISK))));

            // 获取类上的注解
            Anonymous controller = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), Anonymous.class);
            Optional.ofNullable(controller).ifPresent(anonymous->info.getPatternsCondition().getPatterns()
                    .forEach(url->urls.add(RegExUtils.replaceAll(url,PATTERN,ASTERISK))));

        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

}
