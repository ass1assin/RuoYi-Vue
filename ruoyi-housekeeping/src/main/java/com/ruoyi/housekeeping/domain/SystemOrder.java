package com.ruoyi.housekeeping.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 订单管理对象 system_order
 *
 * @author ruoyi
 * @date 2024-12-02
 */
public class SystemOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 订单ID */
    @Excel(name = "订单ID")
    private String orderId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 服务人员ID */
    @Excel(name = "服务人员ID")
    private Long personnelId;

    /** 服务发布ID */
    @Excel(name = "服务发布ID")
    private Long servicePostId;

    /** 服务名称 */
    @Excel(name = "服务名称")
    private String serviceName;

    /** 图片地址 */
    @Excel(name = "图片地址")
    private String imageUrl;

    /** 订单总价 */
    @Excel(name = "订单总价")
    private BigDecimal totalPrice;

    /** 订单状态(0进行中，1已完成，2已取消) */
    @Excel(name = "订单状态(0进行中，1已完成，2已取消)")
    private Long status;

    /** 选择套餐（小时/天） */
    @Excel(name = "选择套餐", readConverterExp = "小=时/天")
    private String orderPackage;

    /** 服务地点 */
    @Excel(name = "服务地点")
    private String location;

    /** 服务ID */
    @Excel(name = "服务ID")
    private Long serviceId;

//    @DateTimeFormat
    private Timestamp startTime;

//    @DateTimeFormat
    private Timestamp endTime;

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }

    public String getOrderId()
    {
        return orderId;
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
    public void setServiceName(String serviceName)
    {
        this.serviceName = serviceName;
    }

    public String getServiceName()
    {
        return serviceName;
    }
    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }
    public void setTotalPrice(BigDecimal totalPrice)
    {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalPrice()
    {
        return totalPrice;
    }
    public void setStatus(Long status)
    {
        this.status = status;
    }

    public Long getStatus()
    {
        return status;
    }
    public void setOrderPackage(String orderPackage)
    {
        this.orderPackage = orderPackage;
    }

    public String getOrderPackage()
    {
        return orderPackage;
    }
    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getLocation()
    {
        return location;
    }
    public void setServiceId(Long serviceId)
    {
        this.serviceId = serviceId;
    }

    public Long getServiceId()
    {
        return serviceId;
    }

//    @Override
//    public String toString() {
//        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
//                .append("id", getId())
//                .append("orderId", getOrderId())
//                .append("userId", getUserId())
//                .append("personnelId", getPersonnelId())
//                .append("servicePostId", getServicePostId())
//                .append("serviceName", getServiceName())
//                .append("imageUrl", getImageUrl())
//                .append("totalPrice", getTotalPrice())
//                .append("status", getStatus())
//                .append("createTime", getCreateTime())
//                .append("orderPackage", getOrderPackage())
//                .append("updateTime", getUpdateTime())
//                .append("location", getLocation())
//                .append("serviceId", getServiceId())
//                .toString();
//    }

    @Override
    public String toString() {
        return "SystemOrder{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", userId=" + userId +
                ", personnelId=" + personnelId +
                ", servicePostId=" + servicePostId +
                ", serviceName='" + serviceName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                ", orderPackage='" + orderPackage + '\'' +
                ", location='" + location + '\'' +
                ", serviceId=" + serviceId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
