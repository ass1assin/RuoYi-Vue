package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SystemServiceImage;

/**
 * 服务图片Service接口
 * 
 * @author ruoyi
 * @date 2024-11-13
 */
public interface ISystemServiceImageService 
{
    /**
     * 查询服务图片
     * 
     * @param id 服务图片主键
     * @return 服务图片
     */
    public SystemServiceImage selectSystemServiceImageById(Long id);

    /**
     * 查询服务图片列表
     * 
     * @param systemServiceImage 服务图片
     * @return 服务图片集合
     */
    public List<SystemServiceImage> selectSystemServiceImageList(SystemServiceImage systemServiceImage);

    /**
     * 新增服务图片
     * 
     * @param systemServiceImage 服务图片
     * @return 结果
     */
    public int insertSystemServiceImage(SystemServiceImage systemServiceImage);

    /**
     * 修改服务图片
     * 
     * @param systemServiceImage 服务图片
     * @return 结果
     */
    public int updateSystemServiceImage(SystemServiceImage systemServiceImage);

    /**
     * 批量删除服务图片
     * 
     * @param ids 需要删除的服务图片主键集合
     * @return 结果
     */
    public int deleteSystemServiceImageByIds(Long[] ids);

    /**
     * 删除服务图片信息
     * 
     * @param id 服务图片主键
     * @return 结果
     */
    public int deleteSystemServiceImageById(Long id);
}
