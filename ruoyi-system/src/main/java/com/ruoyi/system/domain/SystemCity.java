package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 城市对象 system_city
 *
 * @author ruoyi
 * @date 2024-11-18
 */
public class SystemCity implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 城市ID */
    private Long id;

    /** 城市名称 */
    @Excel(name = "城市名称")
    private String cityName;

    /** 开通状态（0：未开通，1：已开通） */
    @Excel(name = "开通状态", readConverterExp = "0=：未开通，1：已开通")
    private Long status;

    /** 城市基础服务费用 */
    @Excel(name = "城市基础服务费用")
    private Long baseServiceFee;

    private List<ServiceDTO> services; // 服务列表


    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }

    public String getCityName()
    {
        return cityName;
    }
    public void setStatus(Long status)
    {
        this.status = status;
    }

    public Long getStatus()
    {
        return status;
    }
    public void setBaseServiceFee(Long baseServiceFee)
    {
        this.baseServiceFee = baseServiceFee;
    }

    public Long getBaseServiceFee()
    {
        return baseServiceFee;
    }

    public List<ServiceDTO> getServices() {
        return services;
    }

    public void setServices(List<ServiceDTO> services) {
        this.services = services;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("cityName", getCityName())
            .append("status", getStatus())
            .append("baseServiceFee", getBaseServiceFee())
            .toString();
    }
}
