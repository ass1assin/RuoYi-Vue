package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 服务种类对象 system_service_category
 *
 * @author ruoyi
 * @date 2024-11-13
 */
public class SystemServiceCategory implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 服务类别ID */
    private Long id;

    /** 服务类别名称 */
    @Excel(name = "服务类别名称")
    private String name;

    /** 服务类别描述 */
    @Excel(name = "服务类别描述")
    private String description;

    private List<SystemService> products;

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

    public List<SystemService> getProducts() {
        return products;
    }

    public void setProducts(List<SystemService> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("description", getDescription())
            .toString();
    }
}
