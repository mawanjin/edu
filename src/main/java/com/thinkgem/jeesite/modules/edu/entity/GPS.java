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
@Table(name = "edu_gps")
@DynamicInsert
@DynamicUpdate
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Gps extends IdEntity<Gps> {
    private String x;
    private String y;
    private String location;
    private Euser user;

    @Basic
    @Column(name = "x")
    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    @Basic
    @Column(name = "y")
    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    @Basic
    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @ManyToOne
    @JoinColumn(name="user")
    public Euser getUser() {
        return user;
    }

    public void setUser(Euser user) {
        this.user = user;
    }
}
