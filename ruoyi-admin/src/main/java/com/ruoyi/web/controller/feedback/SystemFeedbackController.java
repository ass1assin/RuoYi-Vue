package com.ruoyi.web.controller.feedback;

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
import com.ruoyi.housekeeping.domain.SystemFeedback;
import com.ruoyi.housekeeping.service.ISystemFeedbackService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 评价与反馈Controller
 *
 * @author ruoyi
 * @date 2024-07-22
 */
@RestController
@RequestMapping("/housekeeping/feedback")
public class SystemFeedbackController extends BaseController
{
    @Autowired
    private ISystemFeedbackService systemFeedbackService;

    /**
     * 查询评价与反馈列表
     */
    @PreAuthorize("@ss.hasPermi('housekeeping:feedback:list')")
    @GetMapping("/list")
    public TableDataInfo list(SystemFeedback systemFeedback)
    {
        startPage();
        List<SystemFeedback> list = systemFeedbackService.selectSystemFeedbackList(systemFeedback);
        return getDataTable(list);
    }

    /**
     * 导出评价与反馈列表
     */
    @PreAuthorize("@ss.hasPermi('housekeeping:feedback:export')")
    @Log(title = "评价与反馈", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SystemFeedback systemFeedback)
    {
        List<SystemFeedback> list = systemFeedbackService.selectSystemFeedbackList(systemFeedback);
        ExcelUtil<SystemFeedback> util = new ExcelUtil<SystemFeedback>(SystemFeedback.class);
        util.exportExcel(response, list, "评价与反馈数据");
    }

    /**
     * 获取评价与反馈详细信息
     */
    @PreAuthorize("@ss.hasPermi('housekeeping:feedback:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(systemFeedbackService.selectSystemFeedbackById(id));
    }

    /**
     * 新增评价与反馈
     */
    @PreAuthorize("@ss.hasPermi('housekeeping:feedback:add')")
    @Log(title = "评价与反馈", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SystemFeedback systemFeedback)
    {
        return toAjax(systemFeedbackService.insertSystemFeedback(systemFeedback));
    }

    /**
     * 修改评价与反馈
     */
    @PreAuthorize("@ss.hasPermi('housekeeping:feedback:edit')")
    @Log(title = "评价与反馈", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SystemFeedback systemFeedback)
    {
        return toAjax(systemFeedbackService.updateSystemFeedback(systemFeedback));
    }

    /**
     * 删除评价与反馈
     */
    @PreAuthorize("@ss.hasPermi('housekeeping:feedback:remove')")
    @Log(title = "评价与反馈", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(systemFeedbackService.deleteSystemFeedbackByIds(ids));
    }
}
