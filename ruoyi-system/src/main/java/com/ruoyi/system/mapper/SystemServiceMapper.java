package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SystemService;

/**
 * 服务Mapper接口
 * 
 * @author ruoyi
 * @date 2024-11-13
 */
public interface SystemServiceMapper 
{
    /**
     * 查询服务
     * 
     * @param id 服务主键
     * @return 服务
     */
    public SystemService selectSystemServiceById(Long id);

    /**
     * 查询服务列表
     * 
     * @param systemService 服务
     * @return 服务集合
     */
    public List<SystemService> selectSystemServiceList(SystemService systemService);

    /**
     * 新增服务
     * 
     * @param systemService 服务
     * @return 结果
     */
    public int insertSystemService(SystemService systemService);

    /**
     * 修改服务
     * 
     * @param systemService 服务
     * @return 结果
     */
    public int updateSystemService(SystemService systemService);

    /**
     * 删除服务
     * 
     * @param id 服务主键
     * @return 结果
     */
    public int deleteSystemServiceById(Long id);

    /**
     * 批量删除服务
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSystemServiceByIds(Long[] ids);
}
