package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SystemComments;

/**
 * 评论Mapper接口
 * 
 * @author ruoyi
 * @date 2024-12-02
 */
public interface SystemCommentsMapper 
{
    /**
     * 查询评论
     * 
     * @param id 评论主键
     * @return 评论
     */
    public SystemComments selectSystemCommentsById(Long id);

    /**
     * 查询评论列表
     * 
     * @param systemComments 评论
     * @return 评论集合
     */
    public List<SystemComments> selectSystemCommentsList(SystemComments systemComments);

    /**
     * 新增评论
     * 
     * @param systemComments 评论
     * @return 结果
     */
    public int insertSystemComments(SystemComments systemComments);

    /**
     * 修改评论
     * 
     * @param systemComments 评论
     * @return 结果
     */
    public int updateSystemComments(SystemComments systemComments);

    /**
     * 删除评论
     * 
     * @param id 评论主键
     * @return 结果
     */
    public int deleteSystemCommentsById(Long id);

    /**
     * 批量删除评论
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSystemCommentsByIds(Long[] ids);
}
