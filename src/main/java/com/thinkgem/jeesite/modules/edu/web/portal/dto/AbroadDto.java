package com.thinkgem.jeesite.modules.edu.web.portal.dto;

import java.util.Date;

/**
 * Created by wanjinma on 15/4/18.
 */
public class AbroadDto {
    private String id;
    private String title;
    private String img;
    private String summary;
    private String content;
    protected Date createDate;// 创建日期
    private boolean enrolled;
    private Byte enrollable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public boolean isEnrolled() {
        return enrolled;
    }

    public void setEnrolled(boolean isEnrolled) {
        this.enrolled = isEnrolled;
    }

    public Byte getEnrollable() {
        return enrollable;
    }

    public void setEnrollable(Byte enrollable) {
        this.enrollable = enrollable;
    }
}
