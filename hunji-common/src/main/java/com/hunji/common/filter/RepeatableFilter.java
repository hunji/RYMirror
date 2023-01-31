package com.hunji.common.filter;

import cn.hutool.core.util.StrUtil;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 防止重复提交的过滤器
 *
 * @author hunji
 * @version 1.0
 * @date 2023/1/13 10:35
 */
public class RepeatableFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        /**
         * 参数如果是json类型，需要HttpServletRequest包装器进行加强，多次读取参数
         */
        if(request instanceof HttpServletRequest
                && StrUtil.startWithIgnoreCase(request.getContentType(),MediaType.APPLICATION_JSON_VALUE))
        {
            requestWrapper = new RepeatedlyRequestWrapper((HttpServletRequest) request,response);
        }

        if(requestWrapper == null){
            filterChain.doFilter(request,response);
        }else{
            filterChain.doFilter(requestWrapper, response);
        }
    }

}
