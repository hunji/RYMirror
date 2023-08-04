package com.hunji.common.exception.user;

import com.hunji.common.exception.base.BaseException;

/**
 * 用户信息异常类
 *
 * @author hunji
 * @version 1.0
 * @date 2023/7/27 16:26
 */
public class UserException extends BaseException {
    private static final long serialVersionUID = 1L;
    public UserException(String code, Object[] args) {
        super("user",code, args,null);
    }
}
