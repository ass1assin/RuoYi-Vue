package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SystemServiceMapper;
import com.ruoyi.system.domain.SystemService;
import com.ruoyi.system.service.ISystemServiceService;

/**
 * 服务Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-11-13
 */
@Service
public class SystemServiceServiceImpl implements ISystemServiceService 
{
    @Autowired
    private SystemServiceMapper systemServiceMapper;

    /**
     * 查询服务
     * 
     * @param id 服务主键
     * @return 服务
     */
    @Override
    public SystemService selectSystemServiceById(Long id)
    {
        return systemServiceMapper.selectSystemServiceById(id);
    }

    /**
     * 查询服务列表
     * 
     * @param systemService 服务
     * @return 服务
     */
    @Override
    public List<SystemService> selectSystemServiceList(SystemService systemService)
    {
        return systemServiceMapper.selectSystemServiceList(systemService);
    }

    /**
     * 新增服务
     * 
     * @param systemService 服务
     * @return 结果
     */
    @Override
    public int insertSystemService(SystemService systemService)
    {
        return systemServiceMapper.insertSystemService(systemService);
    }

    /**
     * 修改服务
     * 
     * @param systemService 服务
     * @return 结果
     */
    @Override
    public int updateSystemService(SystemService systemService)
    {
        return systemServiceMapper.updateSystemService(systemService);
    }

    /**
     * 批量删除服务
     * 
     * @param ids 需要删除的服务主键
     * @return 结果
     */
    @Override
    public int deleteSystemServiceByIds(Long[] ids)
    {
        return systemServiceMapper.deleteSystemServiceByIds(ids);
    }

    /**
     * 删除服务信息
     * 
     * @param id 服务主键
     * @return 结果
     */
    @Override
    public int deleteSystemServiceById(Long id)
    {
        return systemServiceMapper.deleteSystemServiceById(id);
    }
}
