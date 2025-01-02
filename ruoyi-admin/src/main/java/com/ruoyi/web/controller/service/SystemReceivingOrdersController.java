package com.ruoyi.web.controller.service;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.housekeeping.domain.SystemOrder;
import com.ruoyi.housekeeping.domain.SystemServicePersonnel;
import com.ruoyi.system.service.ISystemReceivingOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/system/receivingOrders")
public class SystemReceivingOrdersController extends BaseController {

    @Autowired
    private ISystemReceivingOrdersService systemReceivingOrdersService;

    /**
     * 查询订单管理列表
     */
    @PreAuthorize("@ss.hasPermi('housekeeping:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(SystemOrder systemOrder)
    {
        startPage();
        List<SystemOrder> list = systemReceivingOrdersService.selectSystemReceivingOrdersList(systemOrder);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('housekeeping:order:list')")
    @GetMapping("/availablePersonnel")
    public TableDataInfo getAvailablePersonnel(SystemOrder systemOrder) {
        startPage();
        List<SystemServicePersonnel> list =systemReceivingOrdersService.getAvailablePersonnel(systemOrder);
        return getDataTable(list);
    }


    /**
     * 导出订单管理列表
     */
    @PreAuthorize("@ss.hasPermi('housekeeping:order:export')")
    @Log(title = "订单管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SystemOrder systemOrder)
    {
        List<SystemOrder> list = systemReceivingOrdersService.selectSystemReceivingOrdersList(systemOrder);
        ExcelUtil<SystemOrder> util = new ExcelUtil<SystemOrder>(SystemOrder.class);
        util.exportExcel(response, list, "订单管理数据");
    }

    /**
     * 获取订单管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('housekeeping:order:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(systemReceivingOrdersService.selectSystemReceivingOrdersById(id));
    }

    /**
     * 新增订单管理
     */
//    @PreAuthorize("@ss.hasPermi('housekeeping:order:add')")
    @Log(title = "订单管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SystemOrder systemOrder)
    {
        return toAjax(systemReceivingOrdersService.insertSystemReceivingOrders(systemOrder));
    }

    /**
     * 修改订单管理
     */
//    @PreAuthorize("@ss.hasPermi('housekeeping:order:edit')")
    @Log(title = "订单管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SystemOrder systemOrder)
    {
        return toAjax(systemReceivingOrdersService.updateSystemReceivingOrders(systemOrder));
    }

    /**
     * 删除订单管理
     */
    @PreAuthorize("@ss.hasPermi('housekeeping:order:remove')")
    @Log(title = "订单管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(systemReceivingOrdersService.deleteSystemReceivingOrdersByIds(ids));
    }
}
