package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 服务对象 system_service
 *
 * @author ruoyi
 * @date 2024-11-13
 */
public class SystemService implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String name;

    private List<SystemServiceImage> images;

    /** 服务描述 */
    @Excel(name = "服务描述")
    private String description;

    /** 类别id */
    @Excel(name = "类别id")
    private Long categoryId;

    /** 价格 */
    @Excel(name = "价格")
    private Long price;

    private String categoryName;

    private BigDecimal hourRate;

    private String hours;

    private String hourlyRate;


    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(String hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public BigDecimal getHourRate() {
        return hourRate;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setHourRate(BigDecimal hourRate) {
        this.hourRate = hourRate;
    }

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

    public List<SystemServiceImage> getImages() {
        return images;
    }

    public void setImages(List<SystemServiceImage> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("description", getDescription())
            .append("categoryId", getCategoryId())
            .append("price", getPrice())
            .toString();
    }
}
