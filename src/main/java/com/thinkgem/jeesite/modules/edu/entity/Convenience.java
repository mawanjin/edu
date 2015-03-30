package com.thinkgem.jeesite.modules.edu.entity;

import com.thinkgem.jeesite.common.persistence.IdEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by abcd on 15/3/24.
 */
@Entity
@Table(name = "edu_convenience")
@DynamicInsert
@DynamicUpdate
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Convenience extends IdEntity<Convenience> {
    private String title;
    private String img;
    private String summary;
    private String content;
    private Byte status;

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "img")
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Basic
    @Column(name = "summary")
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "status")
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Convenience that = (Convenience) o;

        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (createDateEnd != null ? !createDateEnd.equals(that.createDateEnd) : that.createDateEnd != null)
            return false;
        if (createDateStart != null ? !createDateStart.equals(that.createDateStart) : that.createDateStart != null)
            return false;
        if (delFlag != null ? !delFlag.equals(that.delFlag) : that.delFlag != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (img != null ? !img.equals(that.img) : that.img != null) return false;
        if (remarks != null ? !remarks.equals(that.remarks) : that.remarks != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (summary != null ? !summary.equals(that.summary) : that.summary != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (updateDateEnd != null ? !updateDateEnd.equals(that.updateDateEnd) : that.updateDateEnd != null)
            return false;
        if (updateDateStart != null ? !updateDateStart.equals(that.updateDateStart) : that.updateDateStart != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (img != null ? img.hashCode() : 0);
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (delFlag != null ? delFlag.hashCode() : 0);
        result = 31 * result + (remarks != null ? remarks.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (createDateStart != null ? createDateStart.hashCode() : 0);
        result = 31 * result + (createDateEnd != null ? createDateEnd.hashCode() : 0);
        result = 31 * result + (updateDateStart != null ? updateDateStart.hashCode() : 0);
        result = 31 * result + (updateDateEnd != null ? updateDateEnd.hashCode() : 0);
        return result;
    }
}
