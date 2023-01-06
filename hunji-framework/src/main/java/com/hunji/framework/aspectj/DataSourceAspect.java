package com.hunji.framework.aspectj;

import com.hunji.common.annotation.DataSource;
import com.hunji.framework.datasource.DynamicDataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;

import java.util.Objects;

/**
 * 多数据源注解
 *
 * @author hunji
 * @version 1.0
 * @date 2022/12/20 15:43
 */
@Aspect
@Component
@Order(1)
public class DataSourceAspect {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("@annotation(com.hunji.common.annotation.DataSource)||@within(com.hunji.common.annotation.DataSource)")
    public void dsPointCut() {
    }

    @Around("dsPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable{
        DataSource dataSource = getDataSource(point);
        if(dataSource!=null){
            DynamicDataSourceContextHolder.setDataSourceType(dataSource.value().name());
        }

        try {
            return point.proceed();
        } finally {
            // 销毁数据源 在执行方法之后
            DynamicDataSourceContextHolder.clearDataSourceType();
        }
    }

    private DataSource getDataSource(ProceedingJoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        // 查找方法上的注解
        DataSource dataSource = AnnotationUtils.findAnnotation(signature.getMethod(), DataSource.class);
        if(Objects.nonNull(dataSource)){
            return dataSource;
        }
        // 查找类上的注解
        return AnnotationUtils.findAnnotation(signature.getDeclaringType(),DataSource.class);
    }


}
