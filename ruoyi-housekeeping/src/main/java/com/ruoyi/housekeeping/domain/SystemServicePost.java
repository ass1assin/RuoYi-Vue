package com.ruoyi.housekeeping.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 服务发布对象 system_service_post
 * 
 * @author ruoyi
 * @date 2024-07-22
 */
public class SystemServicePost extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 服务发布ID */
    private Long id;

    /** 发布用户ID */
    @Excel(name = "发布用户ID")
    private Long userId;

    /** 服务类别ID */
    @Excel(name = "服务类别ID")
    private Long categoryId;

    /** 服务标题 */
    @Excel(name = "服务标题")
    private String title;

    /** 服务描述 */
    @Excel(name = "服务描述")
    private String description;

    /** 服务价格 */
    @Excel(name = "服务价格")
    private BigDecimal price;

    /** 服务地点 */
    @Excel(name = "服务地点")
    private String location;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }
    public void setLocation(String location) 
    {
        this.location = location;
    }

    public String getLocation() 
    {
        return location;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("categoryId", getCategoryId())
            .append("title", getTitle())
            .append("description", getDescription())
            .append("price", getPrice())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("location", getLocation())
            .toString();
    }
}
