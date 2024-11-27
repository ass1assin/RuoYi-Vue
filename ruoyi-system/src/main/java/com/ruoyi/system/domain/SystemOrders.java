package com.ruoyi.system.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class SystemOrders implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;                   // 主键ID
    private String orderId;              // 订单ID（与数据库中的订单ID字段对应）
    private Long userId;               // 用户ID
    private Long personnelId;          // 服务人员ID
    private Long servicePostId;        // 服务发布ID
    private String serviceName;         // 服务名称（可添加以存储服务名称）
    private String imageUrl;            // 图片地址（可添加以存储服务图片URL）
    private BigDecimal totalPrice;     // 订单总价
    private Integer status;                // 订单状态 (0 - 进行中，1 - 已完成，2 - 已取消)
    private Timestamp createTime;      // 创建时间
    private Timestamp updateTime;      // 更新时间
    private String orderPackage;       // 选择套餐（小时/天）
    private String location;            // 订单地点
    private Long serviceId;            // 服务ID（与数据库中的服务ID字段对应）

    // Getter 和 Setter 方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(Long personnelId) {
        this.personnelId = personnelId;
    }

    public Long getServicePostId() {
        return servicePostId;
    }

    public void setServicePostId(Long servicePostId) {
        this.servicePostId = servicePostId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getOrderPackage() {
        return orderPackage;
    }

    public void setOrderPackage(String orderPackage) {
        this.orderPackage = orderPackage;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
