package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SystemServiceImageMapper;
import com.ruoyi.system.domain.SystemServiceImage;
import com.ruoyi.system.service.ISystemServiceImageService;

/**
 * 服务图片Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-11-13
 */
@Service
public class SystemServiceImageServiceImpl implements ISystemServiceImageService 
{
    @Autowired
    private SystemServiceImageMapper systemServiceImageMapper;

    /**
     * 查询服务图片
     * 
     * @param id 服务图片主键
     * @return 服务图片
     */
    @Override
    public SystemServiceImage selectSystemServiceImageById(Long id)
    {
        return systemServiceImageMapper.selectSystemServiceImageById(id);
    }

    /**
     * 查询服务图片列表
     * 
     * @param systemServiceImage 服务图片
     * @return 服务图片
     */
    @Override
    public List<SystemServiceImage> selectSystemServiceImageList(SystemServiceImage systemServiceImage)
    {
        return systemServiceImageMapper.selectSystemServiceImageList(systemServiceImage);
    }

    /**
     * 新增服务图片
     * 
     * @param systemServiceImage 服务图片
     * @return 结果
     */
    @Override
    public int insertSystemServiceImage(SystemServiceImage systemServiceImage)
    {
        return systemServiceImageMapper.insertSystemServiceImage(systemServiceImage);
    }

    /**
     * 修改服务图片
     * 
     * @param systemServiceImage 服务图片
     * @return 结果
     */
    @Override
    public int updateSystemServiceImage(SystemServiceImage systemServiceImage)
    {
        return systemServiceImageMapper.updateSystemServiceImage(systemServiceImage);
    }

    /**
     * 批量删除服务图片
     * 
     * @param ids 需要删除的服务图片主键
     * @return 结果
     */
    @Override
    public int deleteSystemServiceImageByIds(Long[] ids)
    {
        return systemServiceImageMapper.deleteSystemServiceImageByIds(ids);
    }

    /**
     * 删除服务图片信息
     * 
     * @param id 服务图片主键
     * @return 结果
     */
    @Override
    public int deleteSystemServiceImageById(Long id)
    {
        return systemServiceImageMapper.deleteSystemServiceImageById(id);
    }
}
