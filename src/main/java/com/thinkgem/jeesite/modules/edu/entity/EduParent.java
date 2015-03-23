package com.thinkgem.jeesite.modules.edu.entity;

import com.thinkgem.jeesite.common.persistence.IdEntity;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;

/**
 * Created by abcd on 15/3/5.
 */
@Entity
@Table(name = "edu_parent", schema = "", catalog = "jeesite")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EduParent extends IdEntity<EduParent> {
    private String name;
    private String loginName;
    private String nickName;
    private String passWord;
    private String portrait;
    private Date birth;


    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "login_name")
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Basic
    @Column(name = "nick_name")
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Basic
    @Column(name = "pass_word")
    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Basic
    @Column(name = "portrait")
    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    @Basic
    @Column(name = "birth")
    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EduParent eduParent = (EduParent) o;

        if (birth != null ? !birth.equals(eduParent.birth) : eduParent.birth != null) return false;
        if (delFlag != null ? !delFlag.equals(eduParent.delFlag) : eduParent.delFlag != null) return false;
        if (id != null ? !id.equals(eduParent.id) : eduParent.id != null) return false;
        if (loginName != null ? !loginName.equals(eduParent.loginName) : eduParent.loginName != null) return false;
        if (name != null ? !name.equals(eduParent.name) : eduParent.name != null) return false;
        if (nickName != null ? !nickName.equals(eduParent.nickName) : eduParent.nickName != null) return false;
        if (passWord != null ? !passWord.equals(eduParent.passWord) : eduParent.passWord != null) return false;
        if (portrait != null ? !portrait.equals(eduParent.portrait) : eduParent.portrait != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (loginName != null ? loginName.hashCode() : 0);
        result = 31 * result + (nickName != null ? nickName.hashCode() : 0);
        result = 31 * result + (passWord != null ? passWord.hashCode() : 0);
        result = 31 * result + (portrait != null ? portrait.hashCode() : 0);
        result = 31 * result + (birth != null ? birth.hashCode() : 0);
        result = 31 * result + (delFlag != null ? delFlag.hashCode() : 0);
        return result;
    }

}
