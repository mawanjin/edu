package com.thinkgem.jeesite.modules.edu.entity;

import com.thinkgem.jeesite.common.persistence.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Created by abcd on 15/3/5.
 */
@Entity
@Table(name = "edu_parent_student", schema = "", catalog = "jeesite")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EduParentStudent extends IdEntity<EduParentStudent> {

    private EduStudent eduStudentByStudentId;
    private EduParent eduParentByParentId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EduParentStudent that = (EduParentStudent) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }


    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    public EduStudent getEduStudentByStudentId() {
        return eduStudentByStudentId;
    }

    public void setEduStudentByStudentId(EduStudent eduStudentByStudentId) {
        this.eduStudentByStudentId = eduStudentByStudentId;
    }

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    public EduParent getEduParentByParentId() {
        return eduParentByParentId;
    }

    public void setEduParentByParentId(EduParent eduParentByParentId) {
        this.eduParentByParentId = eduParentByParentId;
    }
}
