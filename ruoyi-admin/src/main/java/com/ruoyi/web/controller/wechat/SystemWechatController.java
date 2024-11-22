package com.ruoyi.web.controller.wechat;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.SystemService;
import com.ruoyi.system.domain.SystemServiceCategory;
import com.ruoyi.system.domain.SystemUserAddress;
import com.ruoyi.system.service.ISystemServiceCategoryService;
import com.ruoyi.system.service.ISystemWechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/wechat")
public class SystemWechatController extends BaseController {

    @Autowired
    private ISystemWechatService systemWechatService;
    @PostMapping
    public AjaxResult add(@RequestBody SystemUserAddress systemUserAddress)
    {
        return toAjax(systemWechatService.insertSystemUserAddress(systemUserAddress));
    }

    @GetMapping("/address")
    public List<SystemUserAddress> getUserAddress(@RequestParam Long userId)
    {
        return systemWechatService.getUserAddress(userId);
    }

}
