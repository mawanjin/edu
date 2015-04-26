package com.thinkgem.jeesite.modules.edu.web.portal.dto;

import com.thinkgem.jeesite.modules.edu.entity.Euser;

/**
 * Created by wanjinma on 15/4/25.
 */
public class LoginDto {
    private boolean rs;
    private Euser euser;
    private String schoolId;
    private String schoolName;
    private Euser guardian;

    public boolean isRs() {
        return rs;
    }

    public void setRs(boolean rs) {
        this.rs = rs;
    }

    public Euser getEuser() {
        return euser;
    }

    public void setEuser(Euser euser) {
        this.euser = euser;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Euser getGuardian() {
        return guardian;
    }

    public void setGuardian(Euser guardian) {
        this.guardian = guardian;
    }
}
