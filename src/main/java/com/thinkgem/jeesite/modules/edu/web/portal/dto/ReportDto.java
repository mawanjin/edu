package com.thinkgem.jeesite.modules.edu.web.portal.dto;

import com.thinkgem.jeesite.modules.edu.entity.Euser;

import java.util.Date;

/**
 * Created by wanjinma on 15/9/6.
 */
public class ReportDto {

    private String id;
    private String title;
    private String content;
    private Byte status;
    private Byte type;//0 周报告 1 月报告 2 学期报告 3 年报告
    private Euser euser;//指定给谁
    private Date createDate;// 创建日期


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

    public Euser getEuser() {
        return euser;
    }

    public void setEuser(Euser euser) {
        this.euser = euser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
