package com.ruoyi.housekeeping.mapper;

import java.util.List;
import com.ruoyi.housekeeping.domain.SystemOrder;

/**
 * 订单管理Mapper接口
 * 
 * @author ruoyi
 * @date 2024-07-22
 */
public interface SystemOrderMapper 
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
     * 删除订单管理
     * 
     * @param id 订单管理主键
     * @return 结果
     */
    public int deleteSystemOrderById(Long id);

    /**
     * 批量删除订单管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSystemOrderByIds(Long[] ids);
}
