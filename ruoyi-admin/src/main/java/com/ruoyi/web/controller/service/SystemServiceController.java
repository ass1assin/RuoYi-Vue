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
import com.ruoyi.system.domain.SystemService;
import com.ruoyi.system.service.ISystemServiceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 服务Controller
 *
 * @author ruoyi
 * @date 2024-11-13
 */
@RestController
@RequestMapping("/system/service")
public class SystemServiceController extends BaseController
{
    @Autowired
    private ISystemServiceService systemServiceService;

    /**
     * 查询服务列表
     */
//    @PreAuthorize("@ss.hasPermi('system:service:list')")
    @GetMapping("/list")
    public TableDataInfo list(SystemService systemService)
    {
        startPage();
        List<SystemService> list = systemServiceService.selectSystemServiceList(systemService);
        return getDataTable(list);
    }

    /**
     * 导出服务列表
     */
    @PreAuthorize("@ss.hasPermi('system:service:export')")
    @Log(title = "服务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SystemService systemService)
    {
        List<SystemService> list = systemServiceService.selectSystemServiceList(systemService);
        ExcelUtil<SystemService> util = new ExcelUtil<SystemService>(SystemService.class);
        util.exportExcel(response, list, "服务数据");
    }

    /**
     * 获取服务详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:service:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(systemServiceService.selectSystemServiceById(id));
    }

    /**
     * 新增服务
     */
    @PreAuthorize("@ss.hasPermi('system:service:add')")
    @Log(title = "服务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SystemService systemService)
    {
        return toAjax(systemServiceService.insertSystemService(systemService));
    }

    /**
     * 修改服务
     */
    @PreAuthorize("@ss.hasPermi('system:service:edit')")
    @Log(title = "服务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SystemService systemService)
    {
        return toAjax(systemServiceService.updateSystemService(systemService));
    }

    /**
     * 删除服务
     */
    @PreAuthorize("@ss.hasPermi('system:service:remove')")
    @Log(title = "服务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(systemServiceService.deleteSystemServiceByIds(ids));
    }
}
