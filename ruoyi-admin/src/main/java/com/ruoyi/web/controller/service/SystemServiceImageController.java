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
import com.ruoyi.system.domain.SystemServiceImage;
import com.ruoyi.system.service.ISystemServiceImageService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 服务图片Controller
 *
 * @author ruoyi
 * @date 2024-11-13
 */
@RestController
@RequestMapping("/system/image")
public class SystemServiceImageController extends BaseController
{
    @Autowired
    private ISystemServiceImageService systemServiceImageService;

    /**
     * 查询服务图片列表
     */
    @PreAuthorize("@ss.hasPermi('system:image:list')")
    @GetMapping("/list")
    public TableDataInfo list(SystemServiceImage systemServiceImage)
    {
        startPage();
        List<SystemServiceImage> list = systemServiceImageService.selectSystemServiceImageList(systemServiceImage);
        return getDataTable(list);
    }

    /**
     * 导出服务图片列表
     */
    @PreAuthorize("@ss.hasPermi('system:image:export')")
    @Log(title = "服务图片", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SystemServiceImage systemServiceImage)
    {
        List<SystemServiceImage> list = systemServiceImageService.selectSystemServiceImageList(systemServiceImage);
        ExcelUtil<SystemServiceImage> util = new ExcelUtil<SystemServiceImage>(SystemServiceImage.class);
        util.exportExcel(response, list, "服务图片数据");
    }

    /**
     * 获取服务图片详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:image:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(systemServiceImageService.selectSystemServiceImageById(id));
    }

    /**
     * 新增服务图片
     */
    @PreAuthorize("@ss.hasPermi('system:image:add')")
    @Log(title = "服务图片", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SystemServiceImage systemServiceImage)
    {
        return toAjax(systemServiceImageService.insertSystemServiceImage(systemServiceImage));
    }

    /**
     * 修改服务图片
     */
    @PreAuthorize("@ss.hasPermi('system:image:edit')")
    @Log(title = "服务图片", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SystemServiceImage systemServiceImage)
    {
        return toAjax(systemServiceImageService.updateSystemServiceImage(systemServiceImage));
    }

    /**
     * 删除服务图片
     */
    @PreAuthorize("@ss.hasPermi('system:image:remove')")
    @Log(title = "服务图片", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(systemServiceImageService.deleteSystemServiceImageByIds(ids));
    }
}
