package com.ruoyi.housekeeping.service;

import java.util.List;
import com.ruoyi.housekeeping.domain.SystemFeedback;

/**
 * 评价与反馈Service接口
 * 
 * @author ruoyi
 * @date 2024-07-22
 */
public interface ISystemFeedbackService 
{
    /**
     * 查询评价与反馈
     * 
     * @param id 评价与反馈主键
     * @return 评价与反馈
     */
    public SystemFeedback selectSystemFeedbackById(Long id);

    /**
     * 查询评价与反馈列表
     * 
     * @param systemFeedback 评价与反馈
     * @return 评价与反馈集合
     */
    public List<SystemFeedback> selectSystemFeedbackList(SystemFeedback systemFeedback);

    /**
     * 新增评价与反馈
     * 
     * @param systemFeedback 评价与反馈
     * @return 结果
     */
    public int insertSystemFeedback(SystemFeedback systemFeedback);

    /**
     * 修改评价与反馈
     * 
     * @param systemFeedback 评价与反馈
     * @return 结果
     */
    public int updateSystemFeedback(SystemFeedback systemFeedback);

    /**
     * 批量删除评价与反馈
     * 
     * @param ids 需要删除的评价与反馈主键集合
     * @return 结果
     */
    public int deleteSystemFeedbackByIds(Long[] ids);

    /**
     * 删除评价与反馈信息
     * 
     * @param id 评价与反馈主键
     * @return 结果
     */
    public int deleteSystemFeedbackById(Long id);
}
