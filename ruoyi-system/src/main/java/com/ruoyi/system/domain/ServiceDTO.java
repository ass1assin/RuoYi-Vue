package com.ruoyi.system.domain;

public class ServiceDTO {
    private Long id;          // 服务ID
    private String name;      // 服务名称
    private Boolean isSelected; // 是否开通

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }
}
