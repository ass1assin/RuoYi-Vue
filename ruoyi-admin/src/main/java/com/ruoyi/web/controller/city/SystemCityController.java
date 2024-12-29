package com.ruoyi.web.controller.city;

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
import com.ruoyi.system.domain.SystemCity;
import com.ruoyi.system.service.ISystemCityService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 城市Controller
 *
 * @author ruoyi
 * @date 2024-11-18
 */
@RestController
@RequestMapping("/system/city")
public class SystemCityController extends BaseController
{
    @Autowired
    private ISystemCityService systemCityService;

    /**
     * 查询城市列表
     */
//    @PreAuthorize("@ss.hasPermi('system:city:list')")
    @GetMapping("/list")
    public TableDataInfo list(SystemCity systemCity)
    {
        startPage();
        List<SystemCity> list = systemCityService.selectSystemCityList(systemCity);
        return getDataTable(list);
    }

    /**
     * 导出城市列表
     */
    @PreAuthorize("@ss.hasPermi('system:city:export')")
    @Log(title = "城市", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SystemCity systemCity)
    {
        List<SystemCity> list = systemCityService.selectSystemCityList(systemCity);
        ExcelUtil<SystemCity> util = new ExcelUtil<SystemCity>(SystemCity.class);
        util.exportExcel(response, list, "城市数据");
    }

    /**
     * 获取城市详细信息
     */
//    @PreAuthorize("@ss.hasPermi('system:city:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(systemCityService.selectSystemCityById(id));
    }

    /**
     * 新增城市
     */
    @PreAuthorize("@ss.hasPermi('system:city:add')")
    @Log(title = "城市", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SystemCity systemCity)
    {
        return toAjax(systemCityService.insertSystemCity(systemCity));
    }

    /**
     * 修改城市
     */
    @PreAuthorize("@ss.hasPermi('system:city:edit')")
    @Log(title = "城市", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SystemCity systemCity)
    {
        return toAjax(systemCityService.updateSystemCity(systemCity));
    }

    /**
     * 删除城市
     */
    @PreAuthorize("@ss.hasPermi('system:city:remove')")
    @Log(title = "城市", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(systemCityService.deleteSystemCityByIds(ids));
    }
}
