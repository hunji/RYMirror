package com.hunji.framework.security.handle;

import cn.hutool.core.util.StrUtil;
import com.hunji.common.core.domain.AjaxResult;
import com.hunji.common.utils.ServletUtils;
import com.alibaba.fastjson2.JSON;
import com.hunji.common.constant.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 *  认证失败处理类 返回未授权
 *
 * @author hunji
 * @version 1.0
 * @date 2023/7/28 14:05
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        int code = HttpStatus.UNAUTHORIZED;
        String msg = StrUtil.format("请求访问：{}，认证失败，无法访问系统资源", request.getRequestURI());
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.error(code, msg)));
    }
}
