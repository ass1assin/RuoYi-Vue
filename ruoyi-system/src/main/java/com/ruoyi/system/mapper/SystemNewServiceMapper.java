package com.ruoyi.system.mapper;

import java.util.List;
import java.util.Map;

import com.ruoyi.system.domain.SystemNewService;
import org.apache.ibatis.annotations.Param;

/**
 * 服务Mapper接口
 *
 * @author ruoyi
 * @date 2024-11-28
 */
public interface SystemNewServiceMapper
{
    /**
     * 查询服务
     *
     * @param id 服务主键
     * @return 服务
     */
    public SystemNewService selectSystemNewServiceById(Long id);

    /**
     * 查询服务列表
     *
     * @param systemNewService 服务
     * @return 服务集合
     */
    public List<SystemNewService> selectSystemNewServiceList(SystemNewService systemNewService);

    /**
     * 新增服务
     *
     * @param systemNewService 服务
     * @return 结果
     */
    public int insertSystemNewService(SystemNewService systemNewService);


    public  boolean insertServiceImages(Map<String, Object> paramMap);  // 批量插入图片路径


    /**
     * 修改服务
     *
     * @param systemNewService 服务
     * @return 结果
     */
    public int updateSystemNewService(SystemNewService systemNewService);

    /**
     * 删除服务
     *
     * @param id 服务主键
     * @return 结果
     */
    public int deleteSystemNewServiceById(Long id);

    /**
     * 批量删除服务
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSystemNewServiceByIds(Long[] ids);

    public boolean insertUserTag(@Param("userId") int userId,@Param("ids") Long[] ids);
}
