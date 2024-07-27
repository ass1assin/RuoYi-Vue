package com.ruoyi.housekeeping.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.housekeeping.mapper.SystemOrderMapper;
import com.ruoyi.housekeeping.domain.SystemOrder;
import com.ruoyi.housekeeping.service.ISystemOrderService;

/**
 * 订单管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-07-22
 */
@Service
public class SystemOrderServiceImpl implements ISystemOrderService 
{
    @Autowired
    private SystemOrderMapper systemOrderMapper;

    /**
     * 查询订单管理
     * 
     * @param id 订单管理主键
     * @return 订单管理
     */
    @Override
    public SystemOrder selectSystemOrderById(Long id)
    {
        return systemOrderMapper.selectSystemOrderById(id);
    }

    /**
     * 查询订单管理列表
     * 
     * @param systemOrder 订单管理
     * @return 订单管理
     */
    @Override
    public List<SystemOrder> selectSystemOrderList(SystemOrder systemOrder)
    {
        return systemOrderMapper.selectSystemOrderList(systemOrder);
    }

    /**
     * 新增订单管理
     * 
     * @param systemOrder 订单管理
     * @return 结果
     */
    @Override
    public int insertSystemOrder(SystemOrder systemOrder)
    {
        systemOrder.setCreateTime(DateUtils.getNowDate());
        return systemOrderMapper.insertSystemOrder(systemOrder);
    }

    /**
     * 修改订单管理
     * 
     * @param systemOrder 订单管理
     * @return 结果
     */
    @Override
    public int updateSystemOrder(SystemOrder systemOrder)
    {
        systemOrder.setUpdateTime(DateUtils.getNowDate());
        return systemOrderMapper.updateSystemOrder(systemOrder);
    }

    /**
     * 批量删除订单管理
     * 
     * @param ids 需要删除的订单管理主键
     * @return 结果
     */
    @Override
    public int deleteSystemOrderByIds(Long[] ids)
    {
        return systemOrderMapper.deleteSystemOrderByIds(ids);
    }

    /**
     * 删除订单管理信息
     * 
     * @param id 订单管理主键
     * @return 结果
     */
    @Override
    public int deleteSystemOrderById(Long id)
    {
        return systemOrderMapper.deleteSystemOrderById(id);
    }
}
