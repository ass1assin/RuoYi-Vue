package com.ruoyi.web.controller.system;


import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.service.MobileLoginService;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.domain.LoginParams;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录控制器
 *
 * @author SK
 * @since 2018/6/13
 */
@RestController
@RequestMapping("/mobile/user")
public class UserController {

    @Autowired
    private ISysUserService sysUserService;


    @Autowired
    private TokenService tokenService;

    @Autowired
    private MobileLoginService loginService;


    /**
     * 注册用户
     *
     * @return -1 用户名或密码错误  -2 账号冻结  -3 账号锁定 1 成功  -4 验证码错误
     */
    @PostMapping("/registerUser")
    @ResponseBody
    public AjaxResult registerUser(HttpServletRequest request) {
        String phoneNo = request.getParameter("phoneNo");
        String validCode = request.getParameter("validCode");
        // 登录结果
        LoginParams loginParams = new LoginParams();
        loginParams.setPhoneNo(phoneNo);
        loginParams.setValidCode(validCode);
        return loginService.registerUser(loginParams);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getUserInfo")
    public AjaxResult getUserInfo(HttpServletRequest request) {
        LoginUser loginUser = tokenService.getLoginUser(request);
        SysUser user = sysUserService.selectUserById(loginUser.getUser().getUserId());
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        return ajax;
    }

    @PostMapping({"saveUserInfo"})
    @ResponseBody
    public AjaxResult saveUserInfo(SysUser user, HttpServletRequest request) {
        AjaxResult ajax = AjaxResult.success("个人信息修改成功！");
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        currentUser = sysUserService.selectUserById(currentUser.getUserId());
        if (StringUtils.isNotBlank(user.getNickName())) {
            currentUser.setNickName(user.getNickName());
        }
        if (StringUtils.isNotBlank(user.getEmail())) {
            currentUser.setEmail(user.getEmail());
        } else {
            currentUser.setEmail("");
        }
        if (StringUtils.isNotBlank(user.getPhonenumber())) {
            currentUser.setPhonenumber(user.getPhonenumber());
        } else {
            currentUser.setPhonenumber("");
        }
        if (StringUtils.isNotBlank(user.getSex())) {
            currentUser.setSex(user.getSex());
        }
        sysUserService.updateUser(currentUser);
        return ajax;
    }
}
