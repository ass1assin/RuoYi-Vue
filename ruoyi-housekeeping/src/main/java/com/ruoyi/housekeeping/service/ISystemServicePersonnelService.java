package com.ruoyi.housekeeping.service;

import java.util.List;
import com.ruoyi.housekeeping.domain.SystemServicePersonnel;

/**
 * 服务人员管理Service接口
 * 
 * @author ruoyi
 * @date 2024-07-22
 */
public interface ISystemServicePersonnelService 
{
    /**
     * 查询服务人员管理
     * 
     * @param id 服务人员管理主键
     * @return 服务人员管理
     */
    public SystemServicePersonnel selectSystemServicePersonnelById(Long id);

    /**
     * 查询服务人员管理列表
     * 
     * @param systemServicePersonnel 服务人员管理
     * @return 服务人员管理集合
     */
    public List<SystemServicePersonnel> selectSystemServicePersonnelList(SystemServicePersonnel systemServicePersonnel);

    /**
     * 新增服务人员管理
     * 
     * @param systemServicePersonnel 服务人员管理
     * @return 结果
     */
    public int insertSystemServicePersonnel(SystemServicePersonnel systemServicePersonnel);

    /**
     * 修改服务人员管理
     * 
     * @param systemServicePersonnel 服务人员管理
     * @return 结果
     */
    public int updateSystemServicePersonnel(SystemServicePersonnel systemServicePersonnel);

    /**
     * 批量删除服务人员管理
     * 
     * @param ids 需要删除的服务人员管理主键集合
     * @return 结果
     */
    public int deleteSystemServicePersonnelByIds(Long[] ids);

    /**
     * 删除服务人员管理信息
     * 
     * @param id 服务人员管理主键
     * @return 结果
     */
    public int deleteSystemServicePersonnelById(Long id);
}
