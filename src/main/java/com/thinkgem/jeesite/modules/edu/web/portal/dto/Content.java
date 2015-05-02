package com.thinkgem.jeesite.modules.edu.web.portal.dto;

/**
 * Created by wanjinma on 15/5/2.
 */
public class Content {
    private Detail address_detail;
    private String address;
    private Point point;

    public Detail getAddress_detail() {
        return address_detail;
    }

    public void setAddress_detail(Detail address_detail) {
        this.address_detail = address_detail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}
