package com.ruoyi.system.domain;

import java.io.Serializable;
import java.util.Date;

public class SystemComment implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;                  // 评论ID
    private Long userId;             // 用户编号
    private Integer serviceId;       // 服务ID
    private Byte rating;             // 评分，使用 Byte 类型与 TINYINT 对应
    private String content;           // 评论内容
    private String imageUrls;         // 评论图片
    private Long parentId;           // 父评论ID
    private Date createTime;          // 创建时间

    // Getter 和 Setter 方法

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Byte getRating() {
        return rating;
    }

    public void setRating(Byte rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

