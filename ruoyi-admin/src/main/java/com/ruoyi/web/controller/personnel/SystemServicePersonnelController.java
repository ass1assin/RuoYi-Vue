package com.ruoyi.web.controller.personnel;

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
import com.ruoyi.housekeeping.domain.SystemServicePersonnel;
import com.ruoyi.housekeeping.service.ISystemServicePersonnelService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 服务人员管理Controller
 *
 * @author ruoyi
 * @date 2024-07-22
 */
@RestController
@RequestMapping("/housekeeping/personnel")
public class SystemServicePersonnelController extends BaseController
{
    @Autowired
    private ISystemServicePersonnelService systemServicePersonnelService;

    /**
     * 查询服务人员管理列表
     */
    @PreAuthorize("@ss.hasPermi('housekeeping:personnel:list')")
    @GetMapping("/list")
    public TableDataInfo list(SystemServicePersonnel systemServicePersonnel)
    {
//        startPage();
        List<SystemServicePersonnel> list = systemServicePersonnelService.selectSystemServicePersonnelList(systemServicePersonnel);
        return getDataTable(list);
    }

    /**
     * 导出服务人员管理列表
     */
    @PreAuthorize("@ss.hasPermi('housekeeping:personnel:export')")
    @Log(title = "服务人员管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SystemServicePersonnel systemServicePersonnel)
    {
        List<SystemServicePersonnel> list = systemServicePersonnelService.selectSystemServicePersonnelList(systemServicePersonnel);
        ExcelUtil<SystemServicePersonnel> util = new ExcelUtil<SystemServicePersonnel>(SystemServicePersonnel.class);
        util.exportExcel(response, list, "服务人员管理数据");
    }

    /**
     * 获取服务人员管理详细信息
     */
//    @PreAuthorize("@ss.hasPermi('housekeeping:personnel:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(systemServicePersonnelService.selectSystemServicePersonnelById(id));
    }

    /**
     * 新增服务人员管理
     */
    @PreAuthorize("@ss.hasPermi('housekeeping:personnel:add')")
    @Log(title = "服务人员管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SystemServicePersonnel systemServicePersonnel)
    {
        return toAjax(systemServicePersonnelService.insertSystemServicePersonnel(systemServicePersonnel));
    }

    /**
     * 修改服务人员管理
     */
    @PreAuthorize("@ss.hasPermi('housekeeping:personnel:edit')")
    @Log(title = "服务人员管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SystemServicePersonnel systemServicePersonnel)
    {
        return toAjax(systemServicePersonnelService.updateSystemServicePersonnel(systemServicePersonnel));
    }

    /**
     * 删除服务人员管理
     */
    @PreAuthorize("@ss.hasPermi('housekeeping:personnel:remove')")
    @Log(title = "服务人员管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(systemServicePersonnelService.deleteSystemServicePersonnelByIds(ids));
    }
}
