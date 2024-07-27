package com.ruoyi.housekeeping.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.housekeeping.mapper.SystemServicePersonnelMapper;
import com.ruoyi.housekeeping.domain.SystemServicePersonnel;
import com.ruoyi.housekeeping.service.ISystemServicePersonnelService;

/**
 * 服务人员管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-07-22
 */
@Service
public class SystemServicePersonnelServiceImpl implements ISystemServicePersonnelService 
{
    @Autowired
    private SystemServicePersonnelMapper systemServicePersonnelMapper;

    /**
     * 查询服务人员管理
     * 
     * @param id 服务人员管理主键
     * @return 服务人员管理
     */
    @Override
    public SystemServicePersonnel selectSystemServicePersonnelById(Long id)
    {
        return systemServicePersonnelMapper.selectSystemServicePersonnelById(id);
    }

    /**
     * 查询服务人员管理列表
     * 
     * @param systemServicePersonnel 服务人员管理
     * @return 服务人员管理
     */
    @Override
    public List<SystemServicePersonnel> selectSystemServicePersonnelList(SystemServicePersonnel systemServicePersonnel)
    {
        return systemServicePersonnelMapper.selectSystemServicePersonnelList(systemServicePersonnel);
    }

    /**
     * 新增服务人员管理
     * 
     * @param systemServicePersonnel 服务人员管理
     * @return 结果
     */
    @Override
    public int insertSystemServicePersonnel(SystemServicePersonnel systemServicePersonnel)
    {
        systemServicePersonnel.setCreateTime(DateUtils.getNowDate());
        return systemServicePersonnelMapper.insertSystemServicePersonnel(systemServicePersonnel);
    }

    /**
     * 修改服务人员管理
     * 
     * @param systemServicePersonnel 服务人员管理
     * @return 结果
     */
    @Override
    public int updateSystemServicePersonnel(SystemServicePersonnel systemServicePersonnel)
    {
        systemServicePersonnel.setUpdateTime(DateUtils.getNowDate());
        return systemServicePersonnelMapper.updateSystemServicePersonnel(systemServicePersonnel);
    }

    /**
     * 批量删除服务人员管理
     * 
     * @param ids 需要删除的服务人员管理主键
     * @return 结果
     */
    @Override
    public int deleteSystemServicePersonnelByIds(Long[] ids)
    {
        return systemServicePersonnelMapper.deleteSystemServicePersonnelByIds(ids);
    }

    /**
     * 删除服务人员管理信息
     * 
     * @param id 服务人员管理主键
     * @return 结果
     */
    @Override
    public int deleteSystemServicePersonnelById(Long id)
    {
        return systemServicePersonnelMapper.deleteSystemServicePersonnelById(id);
    }
}
