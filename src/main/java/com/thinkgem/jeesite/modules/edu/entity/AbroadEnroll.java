package com.thinkgem.jeesite.modules.edu.entity;

import com.thinkgem.jeesite.common.persistence.IdEntity;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Created by abcd on 15/3/24.
 */
@Entity
@Table(name = "edu_abroad_enroll")
@DynamicInsert
@DynamicUpdate
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AbroadEnroll extends IdEntity<AbroadEnroll> {
    private Euser euser;
    private Abroadhome abroadhome;
    private Byte status;


    @Basic
    @Column(name = "status")
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @ManyToOne
    @JoinColumn(name = "user")
    public Euser getEuser() {
        return euser;
    }

    public void setEuser(Euser euser) {
        this.euser = euser;
    }

    @ManyToOne
    @JoinColumn(name = "abroad")
    public Abroadhome getAbroadhome() {
        return abroadhome;
    }

    public void setAbroadhome(Abroadhome abroadhome) {
        this.abroadhome = abroadhome;
    }
}
