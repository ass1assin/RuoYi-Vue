package com.ruoyi.housekeeping.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.housekeeping.mapper.SystemFeedbackMapper;
import com.ruoyi.housekeeping.domain.SystemFeedback;
import com.ruoyi.housekeeping.service.ISystemFeedbackService;

/**
 * 评价与反馈Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-07-22
 */
@Service
public class SystemFeedbackServiceImpl implements ISystemFeedbackService 
{
    @Autowired
    private SystemFeedbackMapper systemFeedbackMapper;

    /**
     * 查询评价与反馈
     * 
     * @param id 评价与反馈主键
     * @return 评价与反馈
     */
    @Override
    public SystemFeedback selectSystemFeedbackById(Long id)
    {
        return systemFeedbackMapper.selectSystemFeedbackById(id);
    }

    /**
     * 查询评价与反馈列表
     * 
     * @param systemFeedback 评价与反馈
     * @return 评价与反馈
     */
    @Override
    public List<SystemFeedback> selectSystemFeedbackList(SystemFeedback systemFeedback)
    {
        return systemFeedbackMapper.selectSystemFeedbackList(systemFeedback);
    }

    /**
     * 新增评价与反馈
     * 
     * @param systemFeedback 评价与反馈
     * @return 结果
     */
    @Override
    public int insertSystemFeedback(SystemFeedback systemFeedback)
    {
        systemFeedback.setCreateTime(DateUtils.getNowDate());
        return systemFeedbackMapper.insertSystemFeedback(systemFeedback);
    }

    /**
     * 修改评价与反馈
     * 
     * @param systemFeedback 评价与反馈
     * @return 结果
     */
    @Override
    public int updateSystemFeedback(SystemFeedback systemFeedback)
    {
        return systemFeedbackMapper.updateSystemFeedback(systemFeedback);
    }

    /**
     * 批量删除评价与反馈
     * 
     * @param ids 需要删除的评价与反馈主键
     * @return 结果
     */
    @Override
    public int deleteSystemFeedbackByIds(Long[] ids)
    {
        return systemFeedbackMapper.deleteSystemFeedbackByIds(ids);
    }

    /**
     * 删除评价与反馈信息
     * 
     * @param id 评价与反馈主键
     * @return 结果
     */
    @Override
    public int deleteSystemFeedbackById(Long id)
    {
        return systemFeedbackMapper.deleteSystemFeedbackById(id);
    }
}
