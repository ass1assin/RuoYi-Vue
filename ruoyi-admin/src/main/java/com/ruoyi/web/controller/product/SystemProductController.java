package com.ruoyi.web.controller.product;

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
import com.ruoyi.housekeeping.domain.SystemProduct;
import com.ruoyi.housekeeping.service.ISystemProductService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 商品管理Controller
 *
 * @author ruoyi
 * @date 2024-07-22
 */
@RestController
@RequestMapping("/housekeeping/product")
public class SystemProductController extends BaseController
{
    @Autowired
    private ISystemProductService systemProductService;

    /**
     * 查询商品管理列表
     */
    @PreAuthorize("@ss.hasPermi('housekeeping:product:list')")
    @GetMapping("/list")
    public TableDataInfo list(SystemProduct systemProduct)
    {
        startPage();
        List<SystemProduct> list = systemProductService.selectSystemProductList(systemProduct);
        return getDataTable(list);
    }

    /**
     * 导出商品管理列表
     */
    @PreAuthorize("@ss.hasPermi('housekeeping:product:export')")
    @Log(title = "商品管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SystemProduct systemProduct)
    {
        List<SystemProduct> list = systemProductService.selectSystemProductList(systemProduct);
        ExcelUtil<SystemProduct> util = new ExcelUtil<SystemProduct>(SystemProduct.class);
        util.exportExcel(response, list, "商品管理数据");
    }

    /**
     * 获取商品管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('housekeeping:product:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(systemProductService.selectSystemProductById(id));
    }

    /**
     * 新增商品管理
     */
    @PreAuthorize("@ss.hasPermi('housekeeping:product:add')")
    @Log(title = "商品管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SystemProduct systemProduct)
    {
        return toAjax(systemProductService.insertSystemProduct(systemProduct));
    }

    /**
     * 修改商品管理
     */
    @PreAuthorize("@ss.hasPermi('housekeeping:product:edit')")
    @Log(title = "商品管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SystemProduct systemProduct)
    {
        return toAjax(systemProductService.updateSystemProduct(systemProduct));
    }

    /**
     * 删除商品管理
     */
    @PreAuthorize("@ss.hasPermi('housekeeping:product:remove')")
    @Log(title = "商品管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(systemProductService.deleteSystemProductByIds(ids));
    }
}
