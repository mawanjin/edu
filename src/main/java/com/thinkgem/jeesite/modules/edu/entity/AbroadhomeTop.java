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
@Table(name = "edu_abroadhome_top")
@DynamicInsert
@DynamicUpdate
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AbroadhomeTop extends IdEntity<AbroadhomeTop> {
    private String img;
    private Byte status;
    private int porder;
//    private Abroadhome abroadhome;

    @Basic
    @Column(name = "img")
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Basic
    @Column(name = "status")
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "porder")
    public int getPorder() {
        return porder;
    }

    public void setPorder(int porder) {
        this.porder = porder;
    }

//    @ManyToOne
//    @JoinColumn(name = "abroadhome")
//    public Abroadhome getAbroadhome() {
//        return abroadhome;
//    }
//
//    public void setAbroadhome(Abroadhome abroadhome) {
//        this.abroadhome = abroadhome;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbroadhomeTop that = (AbroadhomeTop) o;

        if (porder != that.porder) return false;
//        if (abroadhome != null ? !abroadhome.equals(that.abroadhome) : that.abroadhome != null) return false;
        if (img != null ? !img.equals(that.img) : that.img != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = img != null ? img.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + porder;
//        result = 31 * result + (abroadhome != null ? abroadhome.hashCode() : 0);
        return result;
    }
}
