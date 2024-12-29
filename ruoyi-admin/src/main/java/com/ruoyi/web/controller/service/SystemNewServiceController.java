package com.ruoyi.web.controller.service;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SystemNewService;
import com.ruoyi.system.service.ISystemNewServiceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 服务Controller
 *
 * @author ruoyi
 * @date 2024-11-28
 */
@RestController
@RequestMapping("/system/service")
public class SystemNewServiceController extends BaseController
{
    @Autowired
    private ISystemNewServiceService systemNewServiceService;

    /**
     * 查询服务列表
     */
    @PreAuthorize("@ss.hasPermi('system:service:list')")
    @GetMapping("/list")
    public TableDataInfo list(SystemNewService systemNewService)
    {
        startPage();
        List<SystemNewService> list = systemNewServiceService.selectSystemNewServiceList(systemNewService);
        return getDataTable(list);
    }

    /**
     * 导出服务列表
     */
    @PreAuthorize("@ss.hasPermi('system:service:export')")
    @Log(title = "服务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SystemNewService systemNewService)
    {
        List<SystemNewService> list = systemNewServiceService.selectSystemNewServiceList(systemNewService);
        ExcelUtil<SystemNewService> util = new ExcelUtil<SystemNewService>(SystemNewService.class);
        util.exportExcel(response, list, "服务数据");
    }

    /**
     * 获取服务详细信息
     */
//    @PreAuthorize("@ss.hasPermi('system:service:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(systemNewServiceService.selectSystemNewServiceById(id));
    }

    /**
     * 新增服务
     */
    @PreAuthorize("@ss.hasPermi('system:service:add')")
    @Log(title = "服务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SystemNewService systemNewService)
    {
        return toAjax(systemNewServiceService.insertSystemNewService(systemNewService));
    }

    /**
     * 修改服务
     */
    @PreAuthorize("@ss.hasPermi('system:service:edit')")
    @Log(title = "服务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SystemNewService systemNewService)
    {
        return toAjax(systemNewServiceService.updateSystemNewService(systemNewService));
    }

    /**
     * 删除服务
     */
    @PreAuthorize("@ss.hasPermi('system:service:remove')")
    @Log(title = "服务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(systemNewServiceService.deleteSystemNewServiceByIds(ids));
    }
}
