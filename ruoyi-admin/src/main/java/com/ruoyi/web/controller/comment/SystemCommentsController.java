package com.ruoyi.web.controller.comment;

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
import com.ruoyi.system.domain.SystemComments;
import com.ruoyi.system.service.ISystemCommentsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 评论Controller
 *
 * @author ruoyi
 * @date 2024-12-02
 */
@RestController
@RequestMapping("/system/comments")
public class SystemCommentsController extends BaseController
{
    @Autowired
    private ISystemCommentsService systemCommentsService;

    /**
     * 查询评论列表
     */
    @PreAuthorize("@ss.hasPermi('system:comments:list')")
    @GetMapping("/list")
    public TableDataInfo list(SystemComments systemComments)
    {
        startPage();
        List<SystemComments> list = systemCommentsService.selectSystemCommentsList(systemComments);
        return getDataTable(list);
    }

    /**
     * 导出评论列表
     */
    @PreAuthorize("@ss.hasPermi('system:comments:export')")
    @Log(title = "评论", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SystemComments systemComments)
    {
        List<SystemComments> list = systemCommentsService.selectSystemCommentsList(systemComments);
        ExcelUtil<SystemComments> util = new ExcelUtil<SystemComments>(SystemComments.class);
        util.exportExcel(response, list, "评论数据");
    }

    /**
     * 获取评论详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:comments:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(systemCommentsService.selectSystemCommentsById(id));
    }

    /**
     * 新增评论
     */
//    @PreAuthorize("@ss.hasPermi('system:comments:add')")
//    @Log(title = "评论", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SystemComments systemComments)
    {
        return toAjax(systemCommentsService.insertSystemComments(systemComments));
    }

    /**
     * 修改评论
     */
    @PreAuthorize("@ss.hasPermi('system:comments:edit')")
    @Log(title = "评论", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SystemComments systemComments)
    {
        return toAjax(systemCommentsService.updateSystemComments(systemComments));
    }

    /**
     * 删除评论
     */
    @PreAuthorize("@ss.hasPermi('system:comments:remove')")
    @Log(title = "评论", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(systemCommentsService.deleteSystemCommentsByIds(ids));
    }
}
