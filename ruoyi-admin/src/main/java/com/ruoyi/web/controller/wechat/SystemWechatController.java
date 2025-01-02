package com.ruoyi.web.controller.wechat;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.SystemComment;
import com.ruoyi.system.domain.SystemOrders;
import com.ruoyi.system.domain.SystemUserAddress;
import com.ruoyi.system.service.ISystemWechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/wechat")
public class SystemWechatController extends BaseController {

    @Autowired
    private ISystemWechatService systemWechatService;


    @PostMapping
    public AjaxResult addAddress(@RequestBody SystemUserAddress systemUserAddress)
    {
        return toAjax(systemWechatService.insertSystemUserAddress(systemUserAddress));
    }

    @PutMapping("/updateAddress")
    public AjaxResult updateAddress(@RequestBody SystemUserAddress systemUserAddress)
    {
        return toAjax(systemWechatService.updateSystemUserAddress(systemUserAddress));
    }

    @DeleteMapping("/{id}")
    public AjaxResult deleteAddress(@PathVariable Long id)
    {
        return toAjax(systemWechatService.deleteSystemUserAddress(id));
    }

    @GetMapping("/address")
    public List<SystemUserAddress> getUserAddress(@RequestParam(required = false) Long userId)
    {
        return systemWechatService.getUserAddress(userId);
    }

    @GetMapping("/order")
    public List<SystemOrders> getOrder(SystemOrders systemOrders)
    {
        return systemWechatService.getOrder(systemOrders);
    }

    @PostMapping("/createOrder")
    public AjaxResult createOrder(@RequestBody SystemOrders systemOrders)
    {
        return toAjax(systemWechatService.createOrder(systemOrders));
    }

    @PutMapping("/cancelOrder")
    public AjaxResult cancelOrder(@RequestBody SystemOrders systemOrders)
    {
        return toAjax(systemWechatService.cancelOrder(systemOrders));
    }

    @GetMapping("/comment")
    public List<SystemComment> getComment(SystemComment systemComment)
    {
        return systemWechatService.getComment(systemComment);
    }

}
