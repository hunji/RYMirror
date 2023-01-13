package com.hunji.framework.interceptor;

import com.hunji.common.annotation.RepeatSubmit;
import com.hunji.common.core.domain.AjaxResult;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import com.alibaba.fastjson2.JSON;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 防止重复提交拦截器
 *
 * @author hunji
 * @version 1.0
 * @date 2023/1/13 11:25
 */
@Component
public abstract class RepeatSubmitInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 获取到方法上的RepeatSubmit注解建处理
         * 如果判定未重复提交，返回报错
         */
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
            if(annotation!=null){
                if(this.isRepeatSubmit(request,annotation)){
                    AjaxResult ajaxResult = AjaxResult.error(annotation.message());
                    String string = JSON.toJSONString(ajaxResult);
                    response.setStatus(200);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().print(string);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 判断重复提交
     * @param request
     * @param annotation
     * @return
     */
    public abstract boolean isRepeatSubmit(HttpServletRequest request, RepeatSubmit annotation);
}
