package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SystemCity;

/**
 * 城市Mapper接口
 * 
 * @author ruoyi
 * @date 2024-11-18
 */
public interface SystemCityMapper 
{
    /**
     * 查询城市
     * 
     * @param id 城市主键
     * @return 城市
     */
    public SystemCity selectSystemCityById(Long id);

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
     * 删除城市
     * 
     * @param id 城市主键
     * @return 结果
     */
    public int deleteSystemCityById(Long id);

    /**
     * 批量删除城市
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSystemCityByIds(Long[] ids);
}
