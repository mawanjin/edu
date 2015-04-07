package com.thinkgem.jeesite.modules.edu.entity;

import com.thinkgem.jeesite.common.persistence.IdEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Created by abcd on 15/3/24.
 */
@Entity
@Table(name = "edu_teacher")
@DynamicInsert
@DynamicUpdate
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Teacher extends IdEntity<Teacher> {
    private String name;
    private String img;
    private String content;
    private Byte status;
    private Byte gendar;
    private String teachTime;
    private String grade;
    private String ability;
    private String summary;
    private Major major;

    @Basic
    @Column(name = "img")
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "ability")
    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    @Basic
    @Column(name = "grade")
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Basic
    @Column(name = "teach_time")
    public String getTeachTime() {
        return teachTime;
    }

    public void setTeachTime(String teachTime) {
        this.teachTime = teachTime;
    }

    @Basic
    @Column(name = "gendar")
    public Byte getGendar() {
        return gendar;
    }

    public void setGendar(Byte gendar) {
        this.gendar = gendar;
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

    @ManyToOne
    @JoinColumn(name = "major_id", referencedColumnName = "id")
    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }
}
