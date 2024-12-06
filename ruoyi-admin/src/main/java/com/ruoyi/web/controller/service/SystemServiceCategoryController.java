package com.ruoyi.web.controller.service;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.miniprogram.CategoricalDataInfo;
import com.ruoyi.system.domain.SystemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SystemServiceCategory;
import com.ruoyi.system.service.ISystemServiceCategoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 服务种类Controller
 *
 * @author ruoyi
 * @date 2024-11-13
 */
@RestController
@RequestMapping("/system/category")
public class SystemServiceCategoryController extends BaseController
{
    @Autowired
    private ISystemServiceCategoryService systemServiceCategoryService;

    /**
     * 小程序查询服务种类列表
     */
//    @PreAuthorize("@ss.hasPermi('system:category:list')")
    @GetMapping("/list")
    public List<SystemServiceCategory> getServiceCategory(@RequestParam(required = false) String cityName)
    {
        return systemServiceCategoryService.selectSystemServiceCategoryList(cityName);
    }

    @PreAuthorize("@ss.hasPermi('system:category:list')")
    @GetMapping("/yuanlist")
    public TableDataInfo  getServiceCategoryyuan(SystemServiceCategory systemServiceCategory)
    {
        startPage();
        List<SystemServiceCategory> list = systemServiceCategoryService.selectSystemServiceCategoryyuanList(systemServiceCategory);
        return getDataTable(list);
    }

    /**
     * 小程序查询服务种类列表详情页
     */
//    @PreAuthorize("@ss.hasPermi('system:category:list')")
    @GetMapping("/detail")
    public SystemService getServiceCategoryDetail(SystemService systemService)
    {
        return systemServiceCategoryService.selectServiceCategoryDetail(systemService);
    }

    /**
     * 获取服务种类详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:category:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(systemServiceCategoryService.selectSystemServiceCategoryById(id));
    }

    /**
     * 新增服务种类
     */
    @PreAuthorize("@ss.hasPermi('system:category:add')")
    @Log(title = "服务种类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SystemServiceCategory systemServiceCategory)
    {
        return toAjax(systemServiceCategoryService.insertSystemServiceCategory(systemServiceCategory));
    }

    /**
     * 修改服务种类
     */
    @PreAuthorize("@ss.hasPermi('system:category:edit')")
    @Log(title = "服务种类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SystemServiceCategory systemServiceCategory)
    {
        return toAjax(systemServiceCategoryService.updateSystemServiceCategory(systemServiceCategory));
    }

    /**
     * 删除服务种类
     */
    @PreAuthorize("@ss.hasPermi('system:category:remove')")
    @Log(title = "服务种类", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(systemServiceCategoryService.deleteSystemServiceCategoryByIds(ids));
    }

}
