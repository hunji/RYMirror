package com.hunji.framework.interceptor.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.hunji.common.annotation.RepeatSubmit;
import com.hunji.common.constant.CacheConstants;
import com.hunji.common.core.redis.RedisCache;
import com.hunji.common.filter.RepeatedlyRequestWrapper;
import com.hunji.common.utils.http.HttpHelper;
import com.hunji.framework.interceptor.AbstractRepeatSubmitInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 判断请求url和数据是否和上一次相同，
 *
 * @author hunji
 * @version 1.0
 * @date 2023/1/13 11:57
 */
@Component
public class SameUrlDataInterceptor extends AbstractRepeatSubmitInterceptor {

    public final String REPEAT_PARAMS = "repeatParams";

    public final String REPEAT_TIME = "repeatTime";

    /**
     * 令牌自定义标识
     */
    @Value("${token.header}")
    private String header;

    private final RedisCache redisCache;

    public SameUrlDataInterceptor(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    @Override
    public boolean isRepeatSubmit(HttpServletRequest request, RepeatSubmit annotation) {
        String nowParams = "";
        /**
         * 有两类 参数需要分别读取
         * 一种是通过流读取的
         * 一种是通过params读取的
         */
        if(request instanceof RepeatedlyRequestWrapper){
            RepeatedlyRequestWrapper repeatedlyRequest = (RepeatedlyRequestWrapper) request;
            nowParams = HttpHelper.getBodyString(repeatedlyRequest);
        }

        if(StrUtil.isEmpty(nowParams)){
            nowParams = JSON.toJSONString(request.getParameterMap());
        }
        /*
        获取参数后进行拼接redis的key
         */
        Map<String, Object> nowDataMap = new HashMap<>(2);
        nowDataMap.put(REPEAT_PARAMS, nowParams);
        nowDataMap.put(REPEAT_TIME, System.currentTimeMillis());
        // 请求地址
        String url = request.getRequestURI();

        // 唯一值 没有消息头则使用请求地址
        String submitKey=StrUtil.trimToEmpty(request.getHeader(header)) ;

        // 拼接key
        String cacheRepeatKey = CacheConstants.REPEAT_SUBMIT_KEY + url + submitKey;

        Object sessionObj = redisCache.getCacheObject(cacheRepeatKey);
        if(sessionObj != null){
            Map<String, Object> sessionMap = (Map<String, Object>) sessionObj;
            if(sessionMap.containsKey(url)){
                Map<String, Object> preDataMap = (Map<String, Object>) sessionMap.get(url);
                if (compareParams(nowDataMap, preDataMap) && compareTime(nowDataMap, preDataMap, annotation.interval()))
                {
                    return true;
                }
            }
        }
        Map<String, Object> cacheMap = new HashMap<>(1);
        cacheMap.put(url, nowDataMap);
        redisCache.setCacheObject(cacheRepeatKey, cacheMap, annotation.interval(), TimeUnit.MILLISECONDS);
        return false;
    }

    /**
     * 判断参数是否相同
     */
    private boolean compareParams(Map<String, Object> nowMap, Map<String, Object> preMap)
    {
        String nowParams = (String) nowMap.get(REPEAT_PARAMS);
        String preParams = (String) preMap.get(REPEAT_PARAMS);
        return nowParams.equals(preParams);
    }

    /**
     * 判断两次间隔时间
     */
    private boolean compareTime(Map<String, Object> nowMap, Map<String, Object> preMap, int interval)
    {
        long time1 = (Long) nowMap.get(REPEAT_TIME);
        long time2 = (Long) preMap.get(REPEAT_TIME);
        if ((time1 - time2) < interval)
        {
            return true;
        }
        return false;
    }
}
