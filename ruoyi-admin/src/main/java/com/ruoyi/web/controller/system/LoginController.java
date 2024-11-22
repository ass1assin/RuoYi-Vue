package com.ruoyi.web.controller.system;


import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.framework.web.service.MobileLoginService;
import com.ruoyi.framework.web.service.SysLoginService;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.domain.LoginParams;
import com.ruoyi.system.domain.newUser;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录控制器
 *
 * @author SK
 * @since 2018/6/13
 */
@RestController
@RequestMapping("/mobile/login")
public class LoginController{

    @Autowired(required = false)
    private TokenService tokenService;

    @Autowired(required = false)
    private MobileLoginService loginService;

    @Autowired
    private ISysUserService userService;

    @PostMapping("/wx-login")
    public AjaxResult login(@RequestBody Map<String, String> requestBody) {
        String code = requestBody.get("code");

        // 获取 openid 和 session_key
        Map<String, String> wxData = loginService.getOpenidAndSessionKey(code);
        String openid = wxData.get("openid");

        // 查询或创建用户
        newUser user = userService.selectUserByOpenId(openid);

//        if (user == null) {
//            // 如果用户不存在，创建新用户
//            user = new newUser();
//            user.setOpenid(openid);
//
//            userService.createUser(user); // 保存新用户到数据库
//        }

        // 返回用户ID
        return AjaxResult.success(user.getUserId());
    }

    /**
     * 会员登录
     * @return -1 用户名或密码错误  -2 账号冻结  -3 账号锁定 1 成功  -4 验证码错误
     */
    @PostMapping("/loginByPassword")
    @ResponseBody
    public AjaxResult loginByPassword(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String phoneNo = request.getParameter("phoneNo");
        String validCode = request.getParameter("validCode");
        String loginType = request.getParameter("loginType");
        // 登录结果
        LoginParams loginParams = new LoginParams();
        loginParams.setUsername(username);
        loginParams.setPassword(password);
        loginParams.setPhoneNo(phoneNo);
        loginParams.setValidCode(validCode);
        loginParams.setLoginType(loginType);
        return loginService.login(loginParams);

    }
    /**
     * 发送验证码
     */
    @PostMapping("/sendCode")
    @ResponseBody
    public AjaxResult sendRegisterCode(HttpServletRequest request) {
        String phoneNo = request.getParameter("phoneNo");
        String validCodeType = request.getParameter("validCodeType");
        // 登录结果
        LoginParams loginParams = new LoginParams();
        loginParams.setPhoneNo(phoneNo);
        loginParams.setValidCodeType(validCodeType);
        return loginService.sendCode(loginParams);
    }

    @GetMapping("/logout")
    @ResponseBody
    public AjaxResult logout(HttpServletRequest request) {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if(null != loginUser){
            tokenService.delLoginUser(loginUser.getToken());
        }
        return AjaxResult.success("退出成功！");
    }

}
