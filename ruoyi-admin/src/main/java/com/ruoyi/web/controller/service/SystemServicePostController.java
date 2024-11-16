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
import com.ruoyi.housekeeping.domain.SystemServicePost;
import com.ruoyi.housekeeping.service.ISystemServicePostService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 服务发布Controller
 *
 * @author ruoyi
 * @date 2024-07-27
 */
@RestController
@RequestMapping("/housekeeping/post")
public class SystemServicePostController extends BaseController
{
    @Autowired
    private ISystemServicePostService systemServicePostService;

    /**
     * 查询服务发布列表
     */
//    @PreAuthorize("@ss.hasPermi('housekeeping:post:list')")
    @GetMapping("/list")
    public TableDataInfo list(SystemServicePost systemServicePost)
    {
        startPage();
        List<SystemServicePost> list = systemServicePostService.selectSystemServicePostList(systemServicePost);
        return getDataTable(list);
    }

    /**
     * 导出服务发布列表
     */
    @PreAuthorize("@ss.hasPermi('housekeeping:post:export')")
    @Log(title = "服务发布", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SystemServicePost systemServicePost)
    {
        List<SystemServicePost> list = systemServicePostService.selectSystemServicePostList(systemServicePost);
        ExcelUtil<SystemServicePost> util = new ExcelUtil<SystemServicePost>(SystemServicePost.class);
        util.exportExcel(response, list, "服务发布数据");
    }

    /**
     * 获取服务发布详细信息
     */
    @PreAuthorize("@ss.hasPermi('housekeeping:post:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(systemServicePostService.selectSystemServicePostById(id));
    }

    /**
     * 新增服务发布
     */
    @PreAuthorize("@ss.hasPermi('housekeeping:post:add')")
    @Log(title = "服务发布", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SystemServicePost systemServicePost)
    {
        return toAjax(systemServicePostService.insertSystemServicePost(systemServicePost));
    }

    /**
     * 修改服务发布
     */
    @PreAuthorize("@ss.hasPermi('housekeeping:post:edit')")
    @Log(title = "服务发布", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SystemServicePost systemServicePost)
    {
        return toAjax(systemServicePostService.updateSystemServicePost(systemServicePost));
    }

    /**
     * 删除服务发布
     */
    @PreAuthorize("@ss.hasPermi('housekeeping:post:remove')")
    @Log(title = "服务发布", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(systemServicePostService.deleteSystemServicePostByIds(ids));
    }
}
