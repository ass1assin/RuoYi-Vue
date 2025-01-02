package com.ruoyi.system.service;

import java.util.List;

import com.ruoyi.system.domain.CityDTO;
import com.ruoyi.system.domain.SystemCity;

/**
 * 城市Service接口
 *
 * @author ruoyi
 * @date 2024-11-18
 */
public interface ISystemCityService
{
    /**
     * 查询城市
     *
     * @param id 城市主键
     * @return 城市
     */
    public CityDTO selectSystemCityById(Long id);

    /**
     * 查询城市列表
     *
     * @param systemCity 城市
     * @return 城市集合
     */
    public List<SystemCity> selectSystemCityList(SystemCity systemCity);

    /**
     * 新增城市
     *
     * @param systemCity 城市
     * @return 结果
     */
    public int insertSystemCity(SystemCity systemCity);

    /**
     * 修改城市
     *
     * @param systemCity 城市
     * @return 结果
     */
    public int updateSystemCity(SystemCity systemCity);

    /**
     * 批量删除城市
     *
     * @param ids 需要删除的城市主键集合
     * @return 结果
     */
    public int deleteSystemCityByIds(Long[] ids);

    /**
     * 删除城市信息
     *
     * @param id 城市主键
     * @return 结果
     */
    public int deleteSystemCityById(Long id);
}
