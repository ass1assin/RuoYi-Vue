package com.ruoyi.system.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.housekeeping.domain.SystemOrder;
import com.ruoyi.housekeeping.domain.SystemServicePersonnel;
import com.ruoyi.system.mapper.SystemReceivingOrdersMapper;
import com.ruoyi.system.service.ISystemReceivingOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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
//    @Scheduled(cron = "0 * * * * ?")  // 这里的 cron 表达式表示每分钟执行一次
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
        // 从数据库查询数据
        List<SystemServicePersonnel> personnelList = systemReceivingOrdersMapper.getAvailablePersonnel(systemOrder);

        // 将指定日期转换为星期几
        String dayOfWeek = getDayOfWeek(systemOrder.getStartTime()); // 假设 startTime 是指定的日期

        // 过滤掉 workDay 不包含 dayOfWeek 的数据
        return personnelList.stream()
                .filter(personnel -> isDayIncludedInWorkDay(dayOfWeek, personnel.getWorkDay()))
                .collect(Collectors.toList());
    }

    // 工具方法：将日期转换为中文的“周几”
    private String getDayOfWeek(Timestamp date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.CHINA); // 返回如“星期二”的格式
        String dayOfWeek = sdf.format(date);
        return dayOfWeek.replace("星期", "周"); // 将“星期”替换为“周”
    }

    // 工具方法：判断 workDay 是否包含指定的 dayOfWeek
    private boolean isDayIncludedInWorkDay(String dayOfWeek, String workDay) {
        if (workDay == null || workDay.isEmpty()) {
            return false;
        }
        // 如果 workDay 包含“至”，则将其拆分为起始日和结束日
        if (workDay.contains("至")) {
            // 处理范围格式，如“周一至周五”
            String[] range = workDay.split("至");
            if (range.length == 2) {
                String startDay = range[0].trim();
                String endDay = range[1].trim();
                return isDayInRange(dayOfWeek, startDay, endDay);
            }
        }
        else {
            // 处理逗号分隔的格式，如“周一,周二,周三”
            return workDay.contains(dayOfWeek);
        }
        return false;
    }

    // 工具方法：判断 dayOfWeek 是否在 startDay 和 endDay 范围内
    private boolean isDayInRange(String dayOfWeek, String startDay, String endDay) {
        String[] weekDays = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
        int startIndex = -1, endIndex = -1, currentIndex = -1;

        for (int i = 0; i < weekDays.length; i++) {
            if (weekDays[i].equals(startDay)) startIndex = i;
            if (weekDays[i].equals(endDay)) endIndex = i;
            if (weekDays[i].equals(dayOfWeek)) currentIndex = i;
        }
        if (startIndex == -1 || endIndex == -1 || currentIndex == -1) {
            return false;
        }
        if (startIndex <= endIndex) {
            // 正常范围，例如“周一至周五”
            return currentIndex >= startIndex && currentIndex <= endIndex;
        } else {
            // 跨周范围，例如“周五至周二”
            return currentIndex >= startIndex || currentIndex <= endIndex;
        }
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
