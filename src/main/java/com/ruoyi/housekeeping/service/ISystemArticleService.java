package com.ruoyi.housekeeping.service;

import java.util.List;
import com.ruoyi.housekeeping.domain.SystemArticle;

/**
 * 文章管理Service接口
 * 
 * @author ruoyi
 * @date 2024-07-22
 */
public interface ISystemArticleService 
{
    /**
     * 查询文章管理
     * 
     * @param id 文章管理主键
     * @return 文章管理
     */
    public SystemArticle selectSystemArticleById(Long id);

    /**
     * 查询文章管理列表
     * 
     * @param systemArticle 文章管理
     * @return 文章管理集合
     */
    public List<SystemArticle> selectSystemArticleList(SystemArticle systemArticle);

    /**
     * 新增文章管理
     * 
     * @param systemArticle 文章管理
     * @return 结果
     */
    public int insertSystemArticle(SystemArticle systemArticle);

    /**
     * 修改文章管理
     * 
     * @param systemArticle 文章管理
     * @return 结果
     */
    public int updateSystemArticle(SystemArticle systemArticle);

    /**
     * 批量删除文章管理
     * 
     * @param ids 需要删除的文章管理主键集合
     * @return 结果
     */
    public int deleteSystemArticleByIds(Long[] ids);

    /**
     * 删除文章管理信息
     * 
     * @param id 文章管理主键
     * @return 结果
     */
    public int deleteSystemArticleById(Long id);
}
