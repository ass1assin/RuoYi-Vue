package com.ruoyi.housekeeping.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单管理对象 system_order
 * 
 * @author ruoyi
 * @date 2024-07-22
 */
public class SystemOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单ID */
    private Long id;

    /** 预约ID */
    @Excel(name = "预约ID")
    private Long appointmentId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 服务人员ID */
    @Excel(name = "服务人员ID")
    private Long personnelId;

    /** 服务发布ID */
    @Excel(name = "服务发布ID")
    private Long servicePostId;

    /** 订单总价 */
    @Excel(name = "订单总价")
    private BigDecimal totalPrice;

    /** 订单状态 */
    @Excel(name = "订单状态")
    private String status;

    /** 订单地点 */
    @Excel(name = "订单地点")
    private String location;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setAppointmentId(Long appointmentId) 
    {
        this.appointmentId = appointmentId;
    }

    public Long getAppointmentId() 
    {
        return appointmentId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setPersonnelId(Long personnelId) 
    {
        this.personnelId = personnelId;
    }

    public Long getPersonnelId() 
    {
        return personnelId;
    }
    public void setServicePostId(Long servicePostId) 
    {
        this.servicePostId = servicePostId;
    }

    public Long getServicePostId() 
    {
        return servicePostId;
    }
    public void setTotalPrice(BigDecimal totalPrice) 
    {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalPrice() 
    {
        return totalPrice;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
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
            .append("appointmentId", getAppointmentId())
            .append("userId", getUserId())
            .append("personnelId", getPersonnelId())
            .append("servicePostId", getServicePostId())
            .append("totalPrice", getTotalPrice())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("location", getLocation())
            .toString();
    }
}
