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
@Table(name = "edu_country")
@DynamicInsert
@DynamicUpdate
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Country extends IdEntity<Country> {

    private String name;
    private String img;
    private Byte hot;

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    @Column(name = "hot")
    public Byte getHot() {
        return hot;
    }

    public void setHot(Byte hot) {
        this.hot = hot;
    }
}
