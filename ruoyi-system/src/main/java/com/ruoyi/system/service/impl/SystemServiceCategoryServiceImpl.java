package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.system.domain.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SystemServiceCategoryMapper;
import com.ruoyi.system.domain.SystemServiceCategory;
import com.ruoyi.system.service.ISystemServiceCategoryService;

/**
 * 服务种类Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-13
 */
@Service
public class SystemServiceCategoryServiceImpl implements ISystemServiceCategoryService
{
    @Autowired
    private SystemServiceCategoryMapper systemServiceCategoryMapper;

    /**
     * 查询服务种类
     *
     * @param id 服务种类主键
     * @return 服务种类
     */
    @Override
    public SystemServiceCategory selectSystemServiceCategoryById(Long id)
    {
        return systemServiceCategoryMapper.selectSystemServiceCategoryById(id);
    }

    /**
     * 查询服务种类列表
     *
     * @return 服务种类
     */
    @Override
    public List<SystemServiceCategory> selectSystemServiceCategoryList()
    {
        return systemServiceCategoryMapper.selectSystemServiceCategoryList();
    }


    public SystemService selectServiceCategoryDetail(Long id){
        return systemServiceCategoryMapper.selectServiceCategoryDetail(id);
    };

    /**
     * 新增服务种类
     *
     * @param systemServiceCategory 服务种类
     * @return 结果
     */
    @Override
    public int insertSystemServiceCategory(SystemServiceCategory systemServiceCategory)
    {
        return systemServiceCategoryMapper.insertSystemServiceCategory(systemServiceCategory);
    }

    /**
     * 修改服务种类
     *
     * @param systemServiceCategory 服务种类
     * @return 结果
     */
    @Override
    public int updateSystemServiceCategory(SystemServiceCategory systemServiceCategory)
    {
        return systemServiceCategoryMapper.updateSystemServiceCategory(systemServiceCategory);
    }

    /**
     * 批量删除服务种类
     *
     * @param ids 需要删除的服务种类主键
     * @return 结果
     */
    @Override
    public int deleteSystemServiceCategoryByIds(Long[] ids)
    {
        return systemServiceCategoryMapper.deleteSystemServiceCategoryByIds(ids);
    }

    /**
     * 删除服务种类信息
     *
     * @param id 服务种类主键
     * @return 结果
     */
    @Override
    public int deleteSystemServiceCategoryById(Long id)
    {
        return systemServiceCategoryMapper.deleteSystemServiceCategoryById(id);
    }
}
