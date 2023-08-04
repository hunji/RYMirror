package com.hunji.common.exception.user;


/**
 * TODO
 *
 * @author hunji
 * @version 1.0
 * @date 2023/7/27 16:25
 */
public class UserPasswordRetryLimitExceedException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserPasswordRetryLimitExceedException(int retryLimitCount, int lockTime)
    {
        super("user.password.retry.limit.exceed", new Object[] { retryLimitCount, lockTime });
    }
}
