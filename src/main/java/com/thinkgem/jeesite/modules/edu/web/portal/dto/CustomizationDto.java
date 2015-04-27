package com.thinkgem.jeesite.modules.edu.web.portal.dto;

import java.util.Date;

/**
 * Created by wanjinma on 15/4/18.
 */
public class CustomizationDto {
    private String id;
    private String name;
    private String img;
    private String summary;
    protected Date createDate;// 创建日期

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
