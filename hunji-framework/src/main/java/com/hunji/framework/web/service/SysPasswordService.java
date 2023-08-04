package com.hunji.framework.web.service;

import com.hunji.common.constant.CacheConstants;
import com.hunji.common.core.domain.entity.SysUser;
import com.hunji.common.core.redis.RedisCache;
import com.hunji.common.exception.user.UserPasswordNotMatchException;
import com.hunji.common.exception.user.UserPasswordRetryLimitExceedException;
import com.hunji.common.utils.SecurityUtils;
import com.hunji.framework.security.context.AuthenticationContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 登陆密码验证方法
 *
 * @author hunji
 * @version 1.0
 * @date 2023/7/27 16:08
 */
@Component
public class SysPasswordService {
    @Autowired
    private RedisCache redisCache;

    @Value(value = "${user.password.maxRetryCount}")
    private int maxRetryCount;

    @Value(value = "${user.password.lockTime}")
    private int lockTime;

    private String getCacheKey(String username){
        return CacheConstants.PWD_ERR_CNT_KEY + username;
    }

    /**
     * 进行验证的时候进行重试次数控制
     * @param user
     */
    public void validate(SysUser user){
        Authentication usernamePasswordAuthenticationToken = AuthenticationContextHolder.getContext();
        String username = usernamePasswordAuthenticationToken.getName();
        String password = usernamePasswordAuthenticationToken.getCredentials().toString();
        Integer retryCount = redisCache.getCacheObject(getCacheKey(username));

        if (retryCount == null)
        {
            retryCount = 0;
        }

        if(retryCount>=maxRetryCount){
            // TODO:异步记录尝试登陆次数超出日志
            throw new UserPasswordRetryLimitExceedException(maxRetryCount, lockTime);
        }

        if(!matches(user,password)){
            retryCount = retryCount+1;
            // TODO:异步记录登陆失败日志

            // redis记录失败次数
            redisCache.setCacheObject(getCacheKey(username),retryCount,lockTime, TimeUnit.MINUTES);
            throw new UserPasswordNotMatchException();
        }else{
            // 清理redis中记录
            clearLoginRecordCache(username);
        }


    }

    public boolean matches(SysUser user, String rawPassword){
        return SecurityUtils.matchesPassword(rawPassword,user.getPassword());
    }

    /**
     * 清理redis中的登陆信息
     * @param loginName
     */
    public void clearLoginRecordCache(String loginName){
        if(redisCache.hasKey(getCacheKey(loginName))){
            redisCache.deleteObject(getCacheKey(loginName));
        }
    }

}
