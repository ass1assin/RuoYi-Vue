package com.ruoyi.housekeeping.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 服务人员管理对象 system_service_personnel
 * 
 * @author ruoyi
 * @date 2024-07-22
 */
public class SystemServicePersonnel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 服务人员ID */
    private Long id;

    /** 关联的用户ID */
    @Excel(name = "关联的用户ID")
    private Long userId;

    /** 服务人员姓名 */
    @Excel(name = "服务人员姓名")
    private String name;

    /** 服务人员电话 */
    @Excel(name = "服务人员电话")
    private String phone;

    /** 服务人员邮箱 */
    @Excel(name = "服务人员邮箱")
    private String email;

    /** 服务经验 */
    @Excel(name = "服务经验")
    private String experience;

    /** 服务人员地点 */
    @Excel(name = "服务人员地点")
    private String location;

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
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getEmail() 
    {
        return email;
    }
    public void setExperience(String experience) 
    {
        this.experience = experience;
    }

    public String getExperience() 
    {
        return experience;
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
            .append("userId", getUserId())
            .append("name", getName())
            .append("phone", getPhone())
            .append("email", getEmail())
            .append("experience", getExperience())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("location", getLocation())
            .toString();
    }
}
