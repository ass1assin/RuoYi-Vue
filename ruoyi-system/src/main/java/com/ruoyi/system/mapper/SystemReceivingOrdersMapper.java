package com.ruoyi.system.mapper;

import com.ruoyi.housekeeping.domain.SystemOrder;
import com.ruoyi.housekeeping.domain.SystemServicePersonnel;

import java.sql.Timestamp;
import java.util.List;

/**
 * 订单管理Mapper接口
 *
 * @author ruoyi
 * @date 2024-07-22
 */
public interface SystemReceivingOrdersMapper
{
    /**
     * 查询订单管理
     *
     * @param id 订单管理主键
     * @return 订单管理
     */
    public SystemOrder selectSystemReceivingOrdersById(Long id);



    public List<SystemServicePersonnel> getAvailablePersonnel(SystemOrder systemOrder);
    /**
     * 查询订单管理列表
     *
     * @param systemOrder 订单管理
     * @return 订单管理集合
     */
    public List<SystemOrder> selectSystemReceivingOrdersList(SystemOrder systemOrder);


    public List<SystemOrder> selectOrdersToUpdate(Timestamp currentTime);
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
     * 删除订单管理
     *
     * @param id 订单管理主键
     * @return 结果
     */
    public int deleteSystemReceivingOrdersById(Long id);

    /**
     * 批量删除订单管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSystemReceivingOrdersByIds(Long[] ids);
}
