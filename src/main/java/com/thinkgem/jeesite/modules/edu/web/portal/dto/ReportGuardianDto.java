package com.thinkgem.jeesite.modules.edu.web.portal.dto;

import java.util.Date;

/**
 * Created by wanjinma on 15/4/18.
 */
public class ReportGuardianDto {
    private String id;
    private String title;
    private String content;
    private String remarks;
    protected Date createDate;// 创建日期


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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
