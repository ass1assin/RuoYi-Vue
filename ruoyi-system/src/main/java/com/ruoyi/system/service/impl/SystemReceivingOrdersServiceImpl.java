package com.ruoyi.system.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.housekeeping.domain.SystemOrder;
import com.ruoyi.housekeeping.domain.SystemServicePersonnel;
import com.ruoyi.housekeeping.mapper.SystemOrderMapper;
import com.ruoyi.system.mapper.SystemReceivingOrdersMapper;
import com.ruoyi.system.service.ISystemReceivingOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * 订单管理Service业务层处理
 *
 * @author ruoyi
 * @date 2024-07-22
 */
@Service
public class SystemReceivingOrdersServiceImpl implements ISystemReceivingOrdersService
{
    @Autowired
    private SystemReceivingOrdersMapper systemReceivingOrdersMapper;

    /**
     * 查询订单管理
     *
     * @param id 订单管理主键
     * @return 订单管理
     */
    @Override
    public SystemOrder selectSystemReceivingOrdersById(Long id)
    {
        return systemReceivingOrdersMapper.selectSystemReceivingOrdersById(id);
    }

    /**
     * 查询订单管理列表
     *
     * @param systemOrder 订单管理
     * @return 订单管理
     */
    @Override
    public List<SystemOrder> selectSystemReceivingOrdersList(SystemOrder systemOrder)
    {
        return systemReceivingOrdersMapper.selectSystemReceivingOrdersList(systemOrder);
    }

    // 定时任务：每分钟检查一次
    @Scheduled(cron = "0 * * * * ?")  // 这里的 cron 表达式表示每分钟执行一次
    public void checkAndUpdateOrderStatus() {
        // 获取当前时间
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        // 查询结束时间大于当前时间且状态不为2的订单
        List<SystemOrder> ordersToUpdate = systemReceivingOrdersMapper.selectOrdersToUpdate(currentTime);

        // 更新订单状态
        for (SystemOrder order : ordersToUpdate) {
            order.setStatus(2L);  // 设置状态为 2
            systemReceivingOrdersMapper.updateSystemReceivingOrders(order);
        }
    }

    public List<SystemServicePersonnel> getAvailablePersonnel(SystemOrder systemOrder) {
        return systemReceivingOrdersMapper.getAvailablePersonnel(systemOrder);
    }

    /**
     * 新增订单管理
     *
     * @param systemOrder 订单管理
     * @return 结果
     */
    @Override
    public int insertSystemReceivingOrders(SystemOrder systemOrder)
    {
//        systemOrder.setCreateTime(DateUtils.getNowDate());
        return systemReceivingOrdersMapper.insertSystemReceivingOrders(systemOrder);
    }

    /**
     * 修改订单管理
     *
     * @param systemOrder 订单管理
     * @return 结果
     */
    @Override
    public int updateSystemReceivingOrders(SystemOrder systemOrder)
    {
        systemOrder.setUpdateTime(DateUtils.getNowDate());
        return systemReceivingOrdersMapper.updateSystemReceivingOrders(systemOrder);
    }

    /**
     * 批量删除订单管理
     *
     * @param ids 需要删除的订单管理主键
     * @return 结果
     */
    @Override
    public int deleteSystemReceivingOrdersByIds(Long[] ids)
    {
        return systemReceivingOrdersMapper.deleteSystemReceivingOrdersByIds(ids);
    }

    /**
     * 删除订单管理信息
     *
     * @param id 订单管理主键
     * @return 结果
     */
    @Override
    public int deleteSystemReceivingOrdersById(Long id)
    {
        return systemReceivingOrdersMapper.deleteSystemReceivingOrdersById(id);
    }
}
