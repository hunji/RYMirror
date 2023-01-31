package com.hunji.web.controller.system;

import com.hunji.common.core.domain.AjaxResult;
import com.hunji.system.domain.SysUser;
import com.hunji.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * @author hunji
 * @version 1.0
 * @date 2023/1/6 12:06
 */
@RestController
@RequestMapping("/system/user")
public class SysUserController {
    @Autowired
    private ISysUserService userService;
    @GetMapping("/list")
    public AjaxResult list(SysUser user)
    {
        List<SysUser> list = userService.selectUserList(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("list",list);
        return ajax;
    }
}
