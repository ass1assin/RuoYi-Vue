package com.ruoyi.housekeeping.mapper;

import java.util.List;
import com.ruoyi.housekeeping.domain.SystemProduct;

/**
 * 商品管理Mapper接口
 * 
 * @author ruoyi
 * @date 2024-07-22
 */
public interface SystemProductMapper 
{
    /**
     * 查询商品管理
     * 
     * @param id 商品管理主键
     * @return 商品管理
     */
    public SystemProduct selectSystemProductById(Long id);

    /**
     * 查询商品管理列表
     * 
     * @param systemProduct 商品管理
     * @return 商品管理集合
     */
    public List<SystemProduct> selectSystemProductList(SystemProduct systemProduct);

    /**
     * 新增商品管理
     * 
     * @param systemProduct 商品管理
     * @return 结果
     */
    public int insertSystemProduct(SystemProduct systemProduct);

    /**
     * 修改商品管理
     * 
     * @param systemProduct 商品管理
     * @return 结果
     */
    public int updateSystemProduct(SystemProduct systemProduct);

    /**
     * 删除商品管理
     * 
     * @param id 商品管理主键
     * @return 结果
     */
    public int deleteSystemProductById(Long id);

    /**
     * 批量删除商品管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSystemProductByIds(Long[] ids);
}
