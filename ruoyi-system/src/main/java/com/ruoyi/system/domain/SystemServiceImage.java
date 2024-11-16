package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.io.Serializable;

/**
 * 服务图片对象 system_service_image
 *
 * @author ruoyi
 * @date 2024-11-13
 */
public class SystemServiceImage implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 图片路径 */
    @Excel(name = "图片路径")
    private String imageUrl;

    /** 服务id */
    @Excel(name = "服务id")
    private Long serviceId;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setServiceId(Long serviceId)
    {
        this.serviceId = serviceId;
    }

    public Long getServiceId()
    {
        return serviceId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("imageUrl", getImageUrl())
            .append("serviceId", getServiceId())
            .toString();
    }
}
