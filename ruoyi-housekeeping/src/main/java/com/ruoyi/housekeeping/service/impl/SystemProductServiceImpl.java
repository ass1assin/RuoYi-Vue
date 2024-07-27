package com.ruoyi.housekeeping.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.housekeeping.mapper.SystemProductMapper;
import com.ruoyi.housekeeping.domain.SystemProduct;
import com.ruoyi.housekeeping.service.ISystemProductService;

/**
 * 商品管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-07-22
 */
@Service
public class SystemProductServiceImpl implements ISystemProductService 
{
    @Autowired
    private SystemProductMapper systemProductMapper;

    /**
     * 查询商品管理
     * 
     * @param id 商品管理主键
     * @return 商品管理
     */
    @Override
    public SystemProduct selectSystemProductById(Long id)
    {
        return systemProductMapper.selectSystemProductById(id);
    }

    /**
     * 查询商品管理列表
     * 
     * @param systemProduct 商品管理
     * @return 商品管理
     */
    @Override
    public List<SystemProduct> selectSystemProductList(SystemProduct systemProduct)
    {
        return systemProductMapper.selectSystemProductList(systemProduct);
    }

    /**
     * 新增商品管理
     * 
     * @param systemProduct 商品管理
     * @return 结果
     */
    @Override
    public int insertSystemProduct(SystemProduct systemProduct)
    {
        systemProduct.setCreateTime(DateUtils.getNowDate());
        return systemProductMapper.insertSystemProduct(systemProduct);
    }

    /**
     * 修改商品管理
     * 
     * @param systemProduct 商品管理
     * @return 结果
     */
    @Override
    public int updateSystemProduct(SystemProduct systemProduct)
    {
        systemProduct.setUpdateTime(DateUtils.getNowDate());
        return systemProductMapper.updateSystemProduct(systemProduct);
    }

    /**
     * 批量删除商品管理
     * 
     * @param ids 需要删除的商品管理主键
     * @return 结果
     */
    @Override
    public int deleteSystemProductByIds(Long[] ids)
    {
        return systemProductMapper.deleteSystemProductByIds(ids);
    }

    /**
     * 删除商品管理信息
     * 
     * @param id 商品管理主键
     * @return 结果
     */
    @Override
    public int deleteSystemProductById(Long id)
    {
        return systemProductMapper.deleteSystemProductById(id);
    }
}
