package com.ruoyi.system.service;

import com.ruoyi.housekeeping.domain.SystemOrder;
import com.ruoyi.housekeeping.domain.SystemServicePersonnel;

import java.util.List;

/**
 * 订单管理Service接口
 *
 * @author ruoyi
 * @date 2024-07-22
 */
public interface ISystemReceivingOrdersService
{
    /**
     * 查询订单管理
     *
     * @param id 订单管理主键
     * @return 订单管理
     */
    public SystemOrder selectSystemReceivingOrdersById(Long id);

    /**
     * 查询订单管理列表
     *
     * @param systemOrder 订单管理
     * @return 订单管理集合
     */
    public List<SystemOrder> selectSystemReceivingOrdersList(SystemOrder systemOrder);

    public List<SystemServicePersonnel> getAvailablePersonnel(SystemOrder systemOrder);

    /**
     * 新增订单管理
     *
     * @param systemOrder 订单管理
     * @return 结果
     */
    public int insertSystemReceivingOrders(SystemOrder systemOrder);

    /**
     * 修改订单管理
     *
     * @param systemOrder 订单管理
     * @return 结果
     */
    public int updateSystemReceivingOrders(SystemOrder systemOrder);

    /**
     * 批量删除订单管理
     *
     * @param ids 需要删除的订单管理主键集合
     * @return 结果
     */
    public int deleteSystemReceivingOrdersByIds(Long[] ids);

    /**
     * 删除订单管理信息
     *
     * @param id 订单管理主键
     * @return 结果
     */
    public int deleteSystemReceivingOrdersById(Long id);
}
