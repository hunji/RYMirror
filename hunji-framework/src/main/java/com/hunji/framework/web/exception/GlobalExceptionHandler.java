package com.hunji.framework.web.exception;

import cn.hutool.core.util.StrUtil;
import com.hunji.common.core.domain.AjaxResult;
import com.hunji.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 *
 * @author hunji
 * @version 1.0
 * @date 2023/1/9 9:40
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public AjaxResult handleServiceException(ServiceException e, HttpServletRequest request){
        log.error(e.getMessage(), e);
        Integer code = e.getCode();

        return code==null ? AjaxResult.error(code, e.getMessage()) : AjaxResult.error(e.getMessage());
    }
}
