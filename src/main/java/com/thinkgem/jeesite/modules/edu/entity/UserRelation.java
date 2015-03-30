package com.thinkgem.jeesite.modules.edu.entity;

import javax.persistence.*;

/**
 * Created by abcd on 15/3/31.
 */
@Entity
@Table(name = "edu_user_relation", schema = "", catalog = "jeesite")
public class UserRelation {
    private String id;
//    private String studentId;
    private Euser eduUserByParentId;
    private Euser eduUserById;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 40)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false)
    public Euser getEduUserById() {
        return eduUserById;
    }

    public void setEduUserById(Euser eduUserById) {
        this.eduUserById = eduUserById;
    }
}
