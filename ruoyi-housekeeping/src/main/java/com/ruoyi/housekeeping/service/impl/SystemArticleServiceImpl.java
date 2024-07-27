package com.ruoyi.housekeeping.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.housekeeping.mapper.SystemArticleMapper;
import com.ruoyi.housekeeping.domain.SystemArticle;
import com.ruoyi.housekeeping.service.ISystemArticleService;

/**
 * 文章管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-07-22
 */
@Service
public class SystemArticleServiceImpl implements ISystemArticleService 
{
    @Autowired
    private SystemArticleMapper systemArticleMapper;

    /**
     * 查询文章管理
     * 
     * @param id 文章管理主键
     * @return 文章管理
     */
    @Override
    public SystemArticle selectSystemArticleById(Long id)
    {
        return systemArticleMapper.selectSystemArticleById(id);
    }

    /**
     * 查询文章管理列表
     * 
     * @param systemArticle 文章管理
     * @return 文章管理
     */
    @Override
    public List<SystemArticle> selectSystemArticleList(SystemArticle systemArticle)
    {
        return systemArticleMapper.selectSystemArticleList(systemArticle);
    }

    /**
     * 新增文章管理
     * 
     * @param systemArticle 文章管理
     * @return 结果
     */
    @Override
    public int insertSystemArticle(SystemArticle systemArticle)
    {
        systemArticle.setCreateTime(DateUtils.getNowDate());
        return systemArticleMapper.insertSystemArticle(systemArticle);
    }

    /**
     * 修改文章管理
     * 
     * @param systemArticle 文章管理
     * @return 结果
     */
    @Override
    public int updateSystemArticle(SystemArticle systemArticle)
    {
        systemArticle.setUpdateTime(DateUtils.getNowDate());
        return systemArticleMapper.updateSystemArticle(systemArticle);
    }

    /**
     * 批量删除文章管理
     * 
     * @param ids 需要删除的文章管理主键
     * @return 结果
     */
    @Override
    public int deleteSystemArticleByIds(Long[] ids)
    {
        return systemArticleMapper.deleteSystemArticleByIds(ids);
    }

    /**
     * 删除文章管理信息
     * 
     * @param id 文章管理主键
     * @return 结果
     */
    @Override
    public int deleteSystemArticleById(Long id)
    {
        return systemArticleMapper.deleteSystemArticleById(id);
    }
}
