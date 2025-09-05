package com.ruoyi.order.domain;

import java.math.BigDecimal;
import java.util.Date;

public class Order {
    private Long orderId;
    private Long userId;
    private Long productId;
    private Integer quantity;
    private String orderNo;
    private BigDecimal amount;
    private String status;
    private Date createTime;
    private Date updateTime;


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // 添加:
    public Long getProductId() {
        return productId;
    }

    // 添加:
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    // 添加:
    public Integer getQuantity() {
        return quantity;
    }

    // 添加:
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}