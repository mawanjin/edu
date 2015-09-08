package com.thinkgem.jeesite.modules.edu.web.portal.dto;

import com.thinkgem.jeesite.modules.edu.entity.Euser;

/**
 * Created by wanjinma on 15/4/25.
 */
public class RegisterDto {
    private boolean rs;
    private String msg;

    public boolean isRs() {
        return rs;
    }

    public void setRs(boolean rs) {
        this.rs = rs;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
