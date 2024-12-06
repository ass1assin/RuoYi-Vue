package com.ruoyi.system.mapper;

import java.util.List;

import com.ruoyi.system.domain.SystemService;
import com.ruoyi.system.domain.SystemServiceCategory;

/**
 * 服务种类Mapper接口
 *
 * @author ruoyi
 * @date 2024-11-13
 */
public interface SystemServiceCategoryMapper
{
    /**
     * 查询服务种类
     *
     * @param id 服务种类主键
     * @return 服务种类
     */
    public SystemServiceCategory selectSystemServiceCategoryById(Long id);

    /**
     * 查询服务种类列表
     *
     * @return 服务种类集合
     */
    public List<SystemServiceCategory> selectSystemServiceCategoryList(String cityName);

    public List<SystemServiceCategory> selectSystemServiceCategoryyuanList(SystemServiceCategory systemServiceCategory);

    public SystemService selectServiceCategoryDetail(SystemService systemService);

    /**
     * 新增服务种类
     *
     * @param systemServiceCategory 服务种类
     * @return 结果
     */
    public int insertSystemServiceCategory(SystemServiceCategory systemServiceCategory);

    /**
     * 修改服务种类
     *
     * @param systemServiceCategory 服务种类
     * @return 结果
     */
    public int updateSystemServiceCategory(SystemServiceCategory systemServiceCategory);

    /**
     * 删除服务种类
     *
     * @param id 服务种类主键
     * @return 结果
     */
    public int deleteSystemServiceCategoryById(Long id);

    /**
     * 批量删除服务种类
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSystemServiceCategoryByIds(Long[] ids);
}
