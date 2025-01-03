package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 评论对象 system_comment
 *
 * @author ruoyi
 * @date 2024-12-02
 */
public class SystemComments extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 用户编号 */
    @Excel(name = "用户编号")
    private Long userId;

    /** 服务ID */
    @Excel(name = "服务ID")
    private Long serviceId;

    /** 评分 */
    @Excel(name = "评分")
    private Long rating;

    /** 评论内容 */
    @Excel(name = "评论内容")
    private String content;

    /** 评论图片 */
    @Excel(name = "评论图片")
    private String imageUrls;

    /** 父评论 */
    @Excel(name = "父评论")
    private Long parentId;

    private String serviceName;

    private  String userName;

    private String avatar;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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
    public void setServiceId(Long serviceId)
    {
        this.serviceId = serviceId;
    }

    public Long getServiceId()
    {
        return serviceId;
    }
    public void setRating(Long rating)
    {
        this.rating = rating;
    }

    public Long getRating()
    {
        return rating;
    }
    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }
    public void setImageUrls(String imageUrls)
    {
        this.imageUrls = imageUrls;
    }

    public String getImageUrls()
    {
        return imageUrls;
    }
    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public Long getParentId()
    {
        return parentId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("serviceId", getServiceId())
            .append("rating", getRating())
            .append("content", getContent())
            .append("imageUrls", getImageUrls())
            .append("parentId", getParentId())
            .append("createTime", getCreateTime())
            .toString();
    }
}
