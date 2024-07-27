package com.ruoyi.housekeeping.service;

import java.util.List;
import com.ruoyi.housekeeping.domain.SystemOrder;

/**
 * 订单管理Service接口
 * 
 * @author ruoyi
 * @date 2024-07-22
 */
public interface ISystemOrderService 
{
    /**
     * 查询订单管理
     * 
     * @param id 订单管理主键
     * @return 订单管理
     */
    public SystemOrder selectSystemOrderById(Long id);

    /**
     * 查询订单管理列表
     * 
     * @param systemOrder 订单管理
     * @return 订单管理集合
     */
    public List<SystemOrder> selectSystemOrderList(SystemOrder systemOrder);

    /**
     * 新增订单管理
     * 
     * @param systemOrder 订单管理
     * @return 结果
     */
    public int insertSystemOrder(SystemOrder systemOrder);

    /**
     * 修改订单管理
     * 
     * @param systemOrder 订单管理
     * @return 结果
     */
    public int updateSystemOrder(SystemOrder systemOrder);

    /**
     * 批量删除订单管理
     * 
     * @param ids 需要删除的订单管理主键集合
     * @return 结果
     */
    public int deleteSystemOrderByIds(Long[] ids);

    /**
     * 删除订单管理信息
     * 
     * @param id 订单管理主键
     * @return 结果
     */
    public int deleteSystemOrderById(Long id);
}
