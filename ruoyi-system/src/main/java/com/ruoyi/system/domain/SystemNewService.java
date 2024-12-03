package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 服务对象 system_service
 * 
 * @author ruoyi
 * @date 2024-11-28
 */
public class SystemNewService extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /**  */
    @Excel(name = "")
    private String name;

    /** 服务描述 */
    @Excel(name = "服务描述")
    private String description;

    /** 类别id */
    @Excel(name = "类别id")
    private Long categoryId;

    /** 价格 */
    @Excel(name = "价格")
    private Long price;

    /** 供选择的小时数例（1，3，6） */
    @Excel(name = "供选择的小时数例", readConverterExp = "1=，3，6")
    private String hours;

    /** 每小时价格 */
    @Excel(name = "每小时价格")
    private Long hourlyRate;

    /** 供选择的天数例（1，2，3） */
    @Excel(name = "供选择的天数例", readConverterExp = "1=，2，3")
    private Long days;

    /** 每天价格 */
    @Excel(name = "每天价格")
    private Long dailyRate;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }
    public void setPrice(Long price) 
    {
        this.price = price;
    }

    public Long getPrice() 
    {
        return price;
    }
    public void setHours(String hours) 
    {
        this.hours = hours;
    }

    public String getHours() 
    {
        return hours;
    }
    public void setHourlyRate(Long hourlyRate) 
    {
        this.hourlyRate = hourlyRate;
    }

    public Long getHourlyRate() 
    {
        return hourlyRate;
    }
    public void setDays(Long days) 
    {
        this.days = days;
    }

    public Long getDays() 
    {
        return days;
    }
    public void setDailyRate(Long dailyRate) 
    {
        this.dailyRate = dailyRate;
    }

    public Long getDailyRate() 
    {
        return dailyRate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("description", getDescription())
            .append("categoryId", getCategoryId())
            .append("price", getPrice())
            .append("hours", getHours())
            .append("hourlyRate", getHourlyRate())
            .append("days", getDays())
            .append("dailyRate", getDailyRate())
            .toString();
    }
}
