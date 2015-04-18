package com.thinkgem.jeesite.modules.edu.web.portal.dto;

import java.util.Date;

/**
 * Created by wanjinma on 15/4/19.
 */
public class EmergencyDto {
    private String id;
    private String img;
    private String title;
    private String content;
    private Byte status;
    private Byte type; // 0:重要 1:系统
    private Byte top; // 0:普通 1:置顶
    private Date createDate;// 创建日期

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Byte getTop() {
        return top;
    }

    public void setTop(Byte top) {
        this.top = top;
    }
}
