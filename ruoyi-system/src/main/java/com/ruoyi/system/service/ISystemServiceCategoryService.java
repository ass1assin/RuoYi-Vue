package com.ruoyi.system.service;

import java.util.List;

import com.ruoyi.system.domain.SystemService;
import com.ruoyi.system.domain.SystemServiceCategory;

/**
 * 服务种类Service接口
 *
 * @author ruoyi
 * @date 2024-11-13
 */
public interface ISystemServiceCategoryService
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
    public List<SystemServiceCategory> selectSystemServiceCategoryList();


    /**
     * 小程序查询服务种类列表详情页
     */
    public SystemService selectServiceCategoryDetail(Long id);
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
     * 批量删除服务种类
     *
     * @param ids 需要删除的服务种类主键集合
     * @return 结果
     */
    public int deleteSystemServiceCategoryByIds(Long[] ids);

    /**
     * 删除服务种类信息
     *
     * @param id 服务种类主键
     * @return 结果
     */
    public int deleteSystemServiceCategoryById(Long id);
}
