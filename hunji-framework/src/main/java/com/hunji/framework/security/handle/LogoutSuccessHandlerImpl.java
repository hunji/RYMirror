package com.hunji.framework.security.handle;

import com.alibaba.fastjson2.JSON;
import com.hunji.common.constant.HttpStatus;
import com.hunji.common.core.domain.AjaxResult;
import com.hunji.common.core.domain.model.LoginUser;
import com.hunji.common.utils.ServletUtils;
import com.hunji.framework.web.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义退出处理类
 *
 * @author hunji
 * @version 1.0
 * @date 2023/7/28 14:49
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Autowired
    private TokenService tokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if(loginUser!=null){
            String username = loginUser.getUsername();
            // 删除缓存
            tokenService.delLoginUser(loginUser.getToken());
            // TODO:异步记录用户登出日志
        }
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.error(HttpStatus.SUCCESS, "退出成功")));
    }
}
