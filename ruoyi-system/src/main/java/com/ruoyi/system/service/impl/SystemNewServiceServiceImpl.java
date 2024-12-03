package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SystemNewServiceMapper;
import com.ruoyi.system.domain.SystemNewService;
import com.ruoyi.system.service.ISystemNewServiceService;

/**
 * 服务Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-11-28
 */
@Service
public class SystemNewServiceServiceImpl implements ISystemNewServiceService 
{
    @Autowired
    private SystemNewServiceMapper systemNewServiceMapper;

    /**
     * 查询服务
     * 
     * @param id 服务主键
     * @return 服务
     */
    @Override
    public SystemNewService selectSystemNewServiceById(Long id)
    {
        return systemNewServiceMapper.selectSystemNewServiceById(id);
    }

    /**
     * 查询服务列表
     * 
     * @param systemNewService 服务
     * @return 服务
     */
    @Override
    public List<SystemNewService> selectSystemNewServiceList(SystemNewService systemNewService)
    {
        return systemNewServiceMapper.selectSystemNewServiceList(systemNewService);
    }

    /**
     * 新增服务
     * 
     * @param systemNewService 服务
     * @return 结果
     */
    @Override
    public int insertSystemNewService(SystemNewService systemNewService)
    {
        return systemNewServiceMapper.insertSystemNewService(systemNewService);
    }

    /**
     * 修改服务
     * 
     * @param systemNewService 服务
     * @return 结果
     */
    @Override
    public int updateSystemNewService(SystemNewService systemNewService)
    {
        return systemNewServiceMapper.updateSystemNewService(systemNewService);
    }

    /**
     * 批量删除服务
     * 
     * @param ids 需要删除的服务主键
     * @return 结果
     */
    @Override
    public int deleteSystemNewServiceByIds(Long[] ids)
    {
        return systemNewServiceMapper.deleteSystemNewServiceByIds(ids);
    }

    /**
     * 删除服务信息
     * 
     * @param id 服务主键
     * @return 结果
     */
    @Override
    public int deleteSystemNewServiceById(Long id)
    {
        return systemNewServiceMapper.deleteSystemNewServiceById(id);
    }
}
