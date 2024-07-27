package com.ruoyi.housekeeping.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.housekeeping.mapper.SystemServicePostMapper;
import com.ruoyi.housekeeping.domain.SystemServicePost;
import com.ruoyi.housekeeping.service.ISystemServicePostService;

/**
 * 服务发布Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-07-22
 */
@Service
public class SystemServicePostServiceImpl implements ISystemServicePostService 
{
    @Autowired
    private SystemServicePostMapper systemServicePostMapper;

    /**
     * 查询服务发布
     * 
     * @param id 服务发布主键
     * @return 服务发布
     */
    @Override
    public SystemServicePost selectSystemServicePostById(Long id)
    {
        return systemServicePostMapper.selectSystemServicePostById(id);
    }

    /**
     * 查询服务发布列表
     * 
     * @param systemServicePost 服务发布
     * @return 服务发布
     */
    @Override
    public List<SystemServicePost> selectSystemServicePostList(SystemServicePost systemServicePost)
    {
        return systemServicePostMapper.selectSystemServicePostList(systemServicePost);
    }

    /**
     * 新增服务发布
     * 
     * @param systemServicePost 服务发布
     * @return 结果
     */
    @Override
    public int insertSystemServicePost(SystemServicePost systemServicePost)
    {
        systemServicePost.setCreateTime(DateUtils.getNowDate());
        return systemServicePostMapper.insertSystemServicePost(systemServicePost);
    }

    /**
     * 修改服务发布
     * 
     * @param systemServicePost 服务发布
     * @return 结果
     */
    @Override
    public int updateSystemServicePost(SystemServicePost systemServicePost)
    {
        systemServicePost.setUpdateTime(DateUtils.getNowDate());
        return systemServicePostMapper.updateSystemServicePost(systemServicePost);
    }

    /**
     * 批量删除服务发布
     * 
     * @param ids 需要删除的服务发布主键
     * @return 结果
     */
    @Override
    public int deleteSystemServicePostByIds(Long[] ids)
    {
        return systemServicePostMapper.deleteSystemServicePostByIds(ids);
    }

    /**
     * 删除服务发布信息
     * 
     * @param id 服务发布主键
     * @return 结果
     */
    @Override
    public int deleteSystemServicePostById(Long id)
    {
        return systemServicePostMapper.deleteSystemServicePostById(id);
    }
}
