package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SystemCommentsMapper;
import com.ruoyi.system.domain.SystemComments;
import com.ruoyi.system.service.ISystemCommentsService;

/**
 * 评论Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-12-02
 */
@Service
public class SystemCommentsServiceImpl implements ISystemCommentsService 
{
    @Autowired
    private SystemCommentsMapper systemCommentsMapper;

    /**
     * 查询评论
     * 
     * @param id 评论主键
     * @return 评论
     */
    @Override
    public SystemComments selectSystemCommentsById(Long id)
    {
        return systemCommentsMapper.selectSystemCommentsById(id);
    }

    /**
     * 查询评论列表
     * 
     * @param systemComments 评论
     * @return 评论
     */
    @Override
    public List<SystemComments> selectSystemCommentsList(SystemComments systemComments)
    {
        return systemCommentsMapper.selectSystemCommentsList(systemComments);
    }

    /**
     * 新增评论
     * 
     * @param systemComments 评论
     * @return 结果
     */
    @Override
    public int insertSystemComments(SystemComments systemComments)
    {
        systemComments.setCreateTime(DateUtils.getNowDate());
        return systemCommentsMapper.insertSystemComments(systemComments);
    }

    /**
     * 修改评论
     * 
     * @param systemComments 评论
     * @return 结果
     */
    @Override
    public int updateSystemComments(SystemComments systemComments)
    {
        return systemCommentsMapper.updateSystemComments(systemComments);
    }

    /**
     * 批量删除评论
     * 
     * @param ids 需要删除的评论主键
     * @return 结果
     */
    @Override
    public int deleteSystemCommentsByIds(Long[] ids)
    {
        return systemCommentsMapper.deleteSystemCommentsByIds(ids);
    }

    /**
     * 删除评论信息
     * 
     * @param id 评论主键
     * @return 结果
     */
    @Override
    public int deleteSystemCommentsById(Long id)
    {
        return systemCommentsMapper.deleteSystemCommentsById(id);
    }
}
