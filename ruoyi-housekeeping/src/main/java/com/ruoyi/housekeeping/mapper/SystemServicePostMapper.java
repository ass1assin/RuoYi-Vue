package com.ruoyi.housekeeping.mapper;

import java.util.List;
import com.ruoyi.housekeeping.domain.SystemServicePost;

/**
 * 服务发布Mapper接口
 * 
 * @author ruoyi
 * @date 2024-07-22
 */
public interface SystemServicePostMapper 
{
    /**
     * 查询服务发布
     * 
     * @param id 服务发布主键
     * @return 服务发布
     */
    public SystemServicePost selectSystemServicePostById(Long id);

    /**
     * 查询服务发布列表
     * 
     * @param systemServicePost 服务发布
     * @return 服务发布集合
     */
    public List<SystemServicePost> selectSystemServicePostList(SystemServicePost systemServicePost);

    /**
     * 新增服务发布
     * 
     * @param systemServicePost 服务发布
     * @return 结果
     */
    public int insertSystemServicePost(SystemServicePost systemServicePost);

    /**
     * 修改服务发布
     * 
     * @param systemServicePost 服务发布
     * @return 结果
     */
    public int updateSystemServicePost(SystemServicePost systemServicePost);

    /**
     * 删除服务发布
     * 
     * @param id 服务发布主键
     * @return 结果
     */
    public int deleteSystemServicePostById(Long id);

    /**
     * 批量删除服务发布
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSystemServicePostByIds(Long[] ids);
}
