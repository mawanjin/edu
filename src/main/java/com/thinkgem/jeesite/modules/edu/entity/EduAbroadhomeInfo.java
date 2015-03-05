package com.thinkgem.jeesite.modules.edu.entity;

import com.thinkgem.jeesite.common.persistence.IdEntity;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by abcd on 15/3/5.
 */
@Entity
@Table(name = "edu_abroadhome_info", schema = "", catalog = "jeesite")
@DynamicInsert
@DynamicUpdate
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EduAbroadhomeInfo extends IdEntity<EduAbroadhomeInfo> {
    private String summary;

    @Basic
    @Column(name = "summary")
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EduAbroadhomeInfo that = (EduAbroadhomeInfo) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (summary != null ? !summary.equals(that.summary) : that.summary != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        return result;
    }
}
