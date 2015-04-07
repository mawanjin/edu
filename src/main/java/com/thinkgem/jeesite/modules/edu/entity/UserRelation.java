package com.thinkgem.jeesite.modules.edu.entity;

import com.thinkgem.jeesite.common.persistence.IdEntity;

import javax.persistence.*;

/**
 * Created by abcd on 15/3/31.
 */
@Entity
@Table(name = "edu_user_relation", schema = "", catalog = "jeesite")
public class UserRelation extends IdEntity<UserRelation> {
//    private String studentId;
    private Euser eduUserByParentId;
    private Euser eduUserById;



//    @Basic
//    @Column(name = "student_id", nullable = true, insertable = true, updatable = true, length = 40)
//    public String getStudentId() {
//        return studentId;
//    }
//
//    public void setStudentId(String studentId) {
//        this.studentId = studentId;
//    }


    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    public Euser getEduUserByParentId() {
        return eduUserByParentId;
    }

    public void setEduUserByParentId(Euser eduUserByParentId) {
        this.eduUserByParentId = eduUserByParentId;
    }

    @OneToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
    public Euser getEduUserById() {
        return eduUserById;
    }

    public void setEduUserById(Euser eduUserById) {
        this.eduUserById = eduUserById;
    }
}
