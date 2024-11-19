package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SystemCityMapper;
import com.ruoyi.system.domain.SystemCity;
import com.ruoyi.system.service.ISystemCityService;

/**
 * 城市Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-11-18
 */
@Service
public class SystemCityServiceImpl implements ISystemCityService 
{
    @Autowired
    private SystemCityMapper systemCityMapper;

    /**
     * 查询城市
     * 
     * @param id 城市主键
     * @return 城市
     */
    @Override
    public SystemCity selectSystemCityById(Long id)
    {
        return systemCityMapper.selectSystemCityById(id);
    }

    /**
     * 查询城市列表
     * 
     * @param systemCity 城市
     * @return 城市
     */
    @Override
    public List<SystemCity> selectSystemCityList(SystemCity systemCity)
    {
        return systemCityMapper.selectSystemCityList(systemCity);
    }

    /**
     * 新增城市
     * 
     * @param systemCity 城市
     * @return 结果
     */
    @Override
    public int insertSystemCity(SystemCity systemCity)
    {
        return systemCityMapper.insertSystemCity(systemCity);
    }

    /**
     * 修改城市
     * 
     * @param systemCity 城市
     * @return 结果
     */
    @Override
    public int updateSystemCity(SystemCity systemCity)
    {
        return systemCityMapper.updateSystemCity(systemCity);
    }

    /**
     * 批量删除城市
     * 
     * @param ids 需要删除的城市主键
     * @return 结果
     */
    @Override
    public int deleteSystemCityByIds(Long[] ids)
    {
        return systemCityMapper.deleteSystemCityByIds(ids);
    }

    /**
     * 删除城市信息
     * 
     * @param id 城市主键
     * @return 结果
     */
    @Override
    public int deleteSystemCityById(Long id)
    {
        return systemCityMapper.deleteSystemCityById(id);
    }
}
