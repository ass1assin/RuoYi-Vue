package com.ruoyi.framework.web.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.exception.user.UserPasswordNotMatchException;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.system.domain.LoginParams;
import com.ruoyi.system.service.ISysPostService;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 移动端登录服务接口
 */
@Component
public class MobileLoginService  {

    private static final Logger log = LoggerFactory.getLogger(MobileLoginService.class);

    @Autowired(required = false)
    private TokenService tokenService;

    @Autowired(required = false)
    private SysLoginService sysLoginService;

    @Autowired(required = false)
    private ISysRoleService sysRoleService;

    @Autowired(required = false)
    private AuthenticationManager authenticationManager;

    @Autowired(required = false)
    private ISysUserService sysUserService;

    @Autowired(required = false)
    private PermissionService permissionService;

    /**
     * 注入redis服务
     */
    @Autowired(required = false)
    private RedisCache redisCache;


    /**
     * 请求时间戳过期时间5分钟
     */
    private static final int REQUEST_TIME_OUT = 1000 * 60 * 5;


    /**
     * jwt密钥
     */
    @Value("${token.secret}")
    private String jwtSecretKey;


    @Value("${wx.appId}")
    private String appId;

    @Value("${wx.secret}")
    private String appSecret;

    private static final String WX_API_URL = "https://api.weixin.qq.com/sns/jscode2session";

    public Map<String, String> getOpenidAndSessionKey(String code) {
        RestTemplate restTemplate = new RestTemplate();
        String url = WX_API_URL + "?appid=" + appId + "&secret=" + appSecret + "&js_code=" + code + "&grant_type=authorization_code";

        // 调用微信接口
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        JSONObject json = JSON.parseObject(response.getBody());

        if (json.containsKey("openid") && json.containsKey("session_key")) {
            Map<String, String> result = new HashMap<>();
            result.put("openid", json.getString("openid"));
            result.put("session_key", json.getString("session_key"));
            return result;
        } else {
            throw new ServiceException("微信登录失败：" + json.getString("errmsg"));
        }
    }




    public AjaxResult login(LoginParams loginParams) {
        log.debug("login and loginParams:{}", loginParams);

        if (Objects.isNull(loginParams)) {
            return AjaxResult.error(-6,"登录参数不能为空");
        }
        String loginType = loginParams.getLoginType();
        if(StringUtils.isBlank(loginType)){
            return AjaxResult.error(-6,"登录方式不能为空");
        }
        //登录方式0验证码登录，1用户名密码登录，2本机一键登录，3微信单点登录
        if(loginType.equals("0")){
            String phoneNo = loginParams.getPhoneNo();
            if(StringUtils.isBlank(phoneNo)){
                return AjaxResult.error(-6,"登录名不能为空");
            }
            String validCode = loginParams.getValidCode();
            //2表示登录验证码，校验验证码合法性
            //sysSmsSendService.checkValidCode(phoneNo,validCode,"2");
            loginParams.setUsername(phoneNo);
            loginParams.setPassword("SSO_LOGIN");
        }else if(loginType.equals("1")){
            String password = loginParams.getPassword();
            if(StringUtils.isBlank(password)){
                return AjaxResult.error(-6,"密码不能为空");
            }
        }
        // 用户验证
        Authentication authentication = null;
        try
        {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginParams.getUsername(), loginParams.getPassword()));
        }
        catch (Exception e)
        {
            if (e instanceof BadCredentialsException)
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginParams.getUsername(), Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            }
            else
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginParams.getUsername(), Constants.LOGIN_FAIL, e.getMessage()));
                throw new ServiceException(e.getMessage());
            }
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //loginUser.setSource("app");
        SysUser user = loginUser.getUser();
        // 生成token
        String token = tokenService.createToken(loginUser);
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginParams.getUsername(), Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        sysLoginService.recordLoginInfo(user.getUserId());
        //判断用户是否存在管理员角色
        // 角色集合
//        Set<String> roles = permissionService.getRolePermission(user);
//        boolean roleFlag = false;
//        if(!CollectionUtils.isEmpty(roles)){
//            for (String roleKey : roles) {
//                if(roleKey.equals("agent")){
//                    roleFlag = true;
//                    break;
//                }
//            }
//        }
        AjaxResult ajax = AjaxResult.success("");
        ajax.put("token",token);
        //token过期时间
        ajax.put("expired",loginUser.getExpireTime());
        ajax.put("user",loginUser.getUser());
        ajax.put("isAgent",String.valueOf(true));
        return ajax;
    }
    /**
     * 发送注册验证码
     * @param loginParams
     * @return
     */
    public AjaxResult sendCode(LoginParams loginParams) {
        if (Objects.isNull(loginParams)) {
            return AjaxResult.error(-6,"参数为空");
        }
        // 验证验证码
        if (StringUtils.isBlank(loginParams.getPhoneNo())) {
            return AjaxResult.error(-6,"发送手机号不能为空");
        }
        String validCodeType = "2";
        if (StringUtils.isNotBlank(loginParams.getValidCodeType())) {
            validCodeType = loginParams.getValidCodeType();
        }
        try{
            //SysSmsSend sysSmsSend = sysSmsSendService.sendMessage(loginParams.getPhoneNo(),validCodeType,true);
            //String resultFlag = sysSmsSend.getResultFlag();
            String resultFlag = "n";
            if(resultFlag.equals("f")){
                return AjaxResult.error(-6,"对不起手机号【"+loginParams.getPhoneNo()+"】发送短信失败：失败原因:");
            }
        }catch (Exception e){
            throw new ServiceException(e.getMessage());
        }
        AjaxResult ajax = AjaxResult.success("验证码发送成功");
        return ajax;
    }

    /**
     * 手机号验证码注册用户
     * @param loginParams
     * @return
     */
    @Transactional(readOnly = false)
    public AjaxResult registerUser(LoginParams loginParams) {
        try{
            if (Objects.isNull(loginParams)) {
                return AjaxResult.error(-6,"参数为空");
            }
            String phoneNo = loginParams.getPhoneNo();
            if (StringUtils.isBlank(phoneNo)) {
                return AjaxResult.error(-6,"发送手机号不能为空");
            }
            String validCode = loginParams.getValidCode();
            if (StringUtils.isBlank(validCode)) {
                return AjaxResult.error(-6,"验证码不能为空");
            }
            loginParams.setUsername(phoneNo);
            loginParams.setPassword(phoneNo);
            loginParams.setLoginType("1");
            return  this.login(loginParams);
        }catch (Exception e){
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * 设置注册用户角色部门岗位信息
     * @param registerUser
     * @return
     */
    private void setUserDefaultInfo(SysUser registerUser ){
        String registerRoleCode = DictUtils.getDictValue("sys_config","register_role_code","");
        if (StringUtils.isBlank(registerRoleCode)) {
            throw new ServiceException("请前往数据字典【sys_config】中维护注册用户角色编码【register_role_code】");
        }
        String registerDeptCode = DictUtils.getDictValue("sys_config","register_dept_code","");
        if (StringUtils.isBlank(registerDeptCode)) {
            throw new ServiceException("请前往数据字典【sys_config】中维护注册用户部门编码【register_dept_code】");
        }
        String registerPostCode = DictUtils.getDictValue("sys_config","register_post_code","");
        if (StringUtils.isBlank(registerPostCode)) {
            throw new ServiceException("请前往数据字典【sys_config】中维护注册用户岗位编码【register_post_code】");
        }

    }

}
