package com.hunji.system.domain;

import lombok.Data;

/**
 * 人员
 *
 * @author hunji
 * @version 1.0
 * @date 2023/1/6 11:16
 */
@Data
public class SysUser {
    private static final long serialVersionUID = 1L;
    private Long userId;
    private String userName;
    private String nickName;
}
